package com.miml.epson.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.miml.common.utils.PrincipalUtil;
import com.miml.epson.api.client.EpsonApiClient;
import com.miml.epson.api.endPoint.EpsonApiEndPoint;
import com.miml.epson.api.properties.PrintingProperties;
import com.miml.epson.dto.PrinterSettingReqDto;
import com.miml.epson.dto.PrinterSettingResDto;
import com.miml.epson.entity.TokenEntity;
import com.miml.epson.repository.PrinterRepository;
import com.miml.epson.repository.TokenRepository;
import com.miml.security.CustomUserDetails;
import com.miml.user.entity.UserEntity;

@Service
public class PrinterService {

	private final TokenRepository tokenRepository;
	private final PrinterRepository printerRepository;
	private final PrintingProperties printingProperties;
	private final EpsonApiClient epsonApiClient;
	private final PrincipalUtil principalUtil;

	public PrinterService(
			TokenRepository tokenRepository, 
			PrinterRepository printerRepository,
			PrintingProperties printingProperties, 
			EpsonApiClient epsonApiClient,
			PrincipalUtil principalUtil
			) {
		this.tokenRepository = tokenRepository;
		this.printerRepository = printerRepository;
		this.printingProperties = printingProperties;
		this.epsonApiClient = epsonApiClient;
		this.principalUtil = principalUtil;
	}

	ObjectMapper objectMapper = new ObjectMapper();

	public void uploadToExcute(String username, MultipartFile file) throws Exception {
		// 로그인된 유저 정보 조회
		CustomUserDetails customUserDetails =  (CustomUserDetails) principalUtil.getPrincipal();
		UserEntity userEntity = customUserDetails.getUser();
		
		Optional<TokenEntity> optional = tokenRepository.findByUser_IdAndUsername(userEntity.getId(), username);
		
		optional.orElseThrow(() -> new IllegalArgumentException("사용자가 등록한 프린터가 아닙니다. printer email: " + username));
		TokenEntity tokenEntity = optional.get();
		
		// 현재 시간
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
		
		String jobName = "BALLAGAIN" + userEntity.getId() + formattedNow;
		String subjectId = tokenEntity.getSubjectId();
		String accessToken = tokenEntity.getAccessToken();
        
        PrinterSettingReqDto printerSettingReqDto = new PrinterSettingReqDto();
        printerSettingReqDto.setSubjectId(subjectId);
        printerSettingReqDto.setJobName(jobName);
        printerSettingReqDto.setPrintMode("document"); // pdf 고정

        // Setting
        PrinterSettingResDto printerSettingResDto = settingPrint(printerSettingReqDto, accessToken);
        
        File localFile = convertToFile(file);
        // upload
        uploadPrintFile(printerSettingResDto.getUploadUri(), localFile);

        // print
        executePrint(accessToken, subjectId, printerSettingResDto.getId());
    }
	
	public PrinterSettingResDto settingPrint(PrinterSettingReqDto settingReqDto, String accessToken) throws JsonProcessingException {

		String host = printingProperties.getHostName();
		String subjectId = settingReqDto.getSubjectId();
		String endPoint = EpsonApiEndPoint.PRINT_SETTING.replace("{device_id}", subjectId);
		String url = "https://" + host + endPoint;

		// header
		Consumer<HttpHeaders> requestHeader = httpHeaders -> {
			httpHeaders.add("Authorization", "Bearer " + accessToken);
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
		
		// settingPrint 
		PrinterSettingResDto printerSettingResDto = epsonApiClient.post(url, data, PrinterSettingResDto.class, requestHeader);
		
//		PrinterEntity printerEntity = PrinterEntity.builder()
//				.jobId(printerSettingResDto.getId())
//				.
//				.uploadUri(printerSettingResDto.getUploadUri())
//				.build();		
//		printerRepository.save(printerEntity);

		return printerSettingResDto;
	}


	private void uploadPrintFile(String uploadUri, File file) throws IOException {
		String fileName = "1" + getFileExtension(file.getName());
		String url = uploadUri + "&File=" + fileName;

		Consumer<HttpHeaders> requestHeader = httpHeaders -> {
			httpHeaders.add("Content-Length", String.valueOf(file.length()));
			httpHeaders.add("Content-Type", "application/octet-stream");
		};

		byte[] fileData;
		try (FileInputStream fis = new FileInputStream(file)) {
			fileData = fis.readAllBytes();
		}

		Object object = epsonApiClient.post(url, fileData, Object.class, requestHeader);
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
