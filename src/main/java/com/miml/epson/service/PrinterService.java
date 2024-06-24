package com.miml.epson.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.miml.epson.api.client.EpsonApiClient;
import com.miml.epson.api.endPoint.EpsonApiEndPoint;
import com.miml.epson.api.properties.PrintingProperties;
import com.miml.epson.dto.PrinterDto;
import com.miml.epson.dto.PrinterDto.PrinterSettingReqDto;
import com.miml.epson.dto.PrinterDto.PrinterSettingResDto;
import com.miml.epson.entity.PrinterEntity;
import com.miml.epson.entity.TokenEntity;
import com.miml.epson.repository.PrinterRepository;
import com.miml.epson.repository.TokenRepository;

@Service
public class PrinterService {

	private final TokenRepository tokenRepository;
	private final PrinterRepository printerRepository;
	private final PrintingProperties printingProperties;
	private final EpsonApiClient epsonApiClient;

	public PrinterService(TokenRepository tokenRepository, PrinterRepository printerRepository,
			PrintingProperties printingProperties, EpsonApiClient epsonApiClient) {
		this.tokenRepository = tokenRepository;
		this.printerRepository = printerRepository;
		this.printingProperties = printingProperties;
		this.epsonApiClient = epsonApiClient;
	}

	ObjectMapper objectMapper = new ObjectMapper();

	public PrinterSettingResDto settingPrint(PrinterSettingReqDto settingReqDto) throws JsonProcessingException {

		String host = printingProperties.getHostName();
		String subjectId = settingReqDto.getSubjectId();
		String endPoint = EpsonApiEndPoint.PRINT_SETTING.replace("{device_id}", subjectId);
		String url = "https://" + host + endPoint;

		Optional<TokenEntity> optional = tokenRepository.findBySubjectId(subjectId);

		optional.orElseThrow(() -> new IllegalArgumentException("TokenEntity not found for subjectId: " + subjectId));
		TokenEntity tokenEntity = optional.get();

		// header
		Consumer<HttpHeaders> requestHeader = httpHeaders -> {
			httpHeaders.add("Authorization", "Bearer " + tokenEntity.getAccessToken());
			httpHeaders.add("Content-Type", "application/json;charset=utf-8");
		};

		// data
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();

		// settingReqDto에서 필요한 값만 추출하여 설정
		objectNode.put("job_name", settingReqDto.getJobName());
		objectNode.put("print_mode", settingReqDto.getPrintMode());

		// ObjectNode를 JSON 문자열로 변환
		String jsonString = objectMapper.writeValueAsString(objectNode);

		// JSON 문자열을 UTF-8로 인코딩
		byte[] data = jsonString.getBytes(StandardCharsets.UTF_8);
		PrinterEntity printerEntity = epsonApiClient.post(url, data, PrinterEntity.class, requestHeader);

		printerRepository.save(printerEntity);

		PrinterSettingResDto settingResDto = new PrinterSettingResDto();

		settingResDto.setJobId(printerEntity.getJobId());
		settingResDto.setUploadUrl(printerEntity.getUploadUri());

		return settingResDto;
	}

	public void uploadPrintFile(PrinterDto printerDto, MultipartFile file) throws Exception {
        String subjectId = printerDto.getSubjectId();
        Optional<TokenEntity> optional = tokenRepository.findBySubjectId(subjectId);

        if (!optional.isPresent()) {
            throw new IllegalArgumentException("TokenEntity not found for subjectId: " + subjectId);
        }

        TokenEntity tokenEntity = optional.get();
        String accessToken = tokenEntity.getAccessToken();
        
        PrinterSettingReqDto printerSettingReqDto = new PrinterSettingReqDto();
        printerSettingReqDto.setSubjectId(subjectId);
        printerSettingReqDto.setJobName(printerDto.getJobName());
        printerSettingReqDto.setPrintMode(printerDto.getPrintMode());

        // Setting
        PrinterSettingResDto printerSettingResDto = settingPrint(printerSettingReqDto);
        
        String jobId = (String) printerSettingResDto.getJobId();
        String uploadUri = (String) printerSettingResDto.getUploadUrl();
        
        File localFile = convertToFile(file);
        
        // upload
        uploadPrintFile(uploadUri, localFile);

        // print
        executePrint(accessToken, subjectId, jobId);
    }


	private void uploadPrintFile(String uploadUri, File file) throws IOException {
	    String fileName = "1" + getFileExtension(file.getName());
	    String url = EpsonApiEndPoint.UPLOAD_PRINT_FILE.replace("{upload_uri}", uploadUri).replace("{extension}", getFileExtension(fileName));

	    Consumer<HttpHeaders> requestHeader = httpHeaders -> {
	        httpHeaders.add("Content-Length", String.valueOf(file.length()));
	        httpHeaders.add("Content-Type", "application/octet-stream");
	    };

	    byte[] fileData;
	    try (FileInputStream fis = new FileInputStream(file)) {
	        fileData = fis.readAllBytes();
	    }

	    Object object =  epsonApiClient.post(url, fileData, Object.class, requestHeader);
	}

	private void executePrint(String accessToken, String subjectId, String jobId) throws JsonProcessingException {
	    String host = printingProperties.getHostName();
	    String printUri = "https://" + host + EpsonApiEndPoint.EXECUTE_PRINT.replace("{device_id}", subjectId).replace("{job_id}", jobId);
	    
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();

		// ObjectNode를 JSON 문자열로 변환
		String jsonString = objectMapper.writeValueAsString(objectNode);

		// JSON 문자열을 UTF-8로 인코딩
		byte[] data = jsonString.getBytes(StandardCharsets.UTF_8);
	    
	    Consumer<HttpHeaders> requestHeader = httpHeaders -> {
	        httpHeaders.add("Authorization", "Bearer " + accessToken);
	        httpHeaders.add("Content-Type", "application/json; charset=utf-8");
	    };

	    Object object = epsonApiClient.post(printUri, data, Object.class, requestHeader);
	}


    private File convertToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }

}
