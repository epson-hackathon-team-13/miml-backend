package com.miml.epson.service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.miml.epson.api.client.EpsonApiClient;
import com.miml.epson.api.endPoint.EpsonApiEndPoint;
import com.miml.epson.api.properties.PrintingProperties;
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
    
	public PrinterService(
			TokenRepository tokenRepository, 
			PrinterRepository printerRepository,
			PrintingProperties printingProperties, 
			EpsonApiClient epsonApiClient
			) {
		this.tokenRepository = tokenRepository;
		this.printerRepository = printerRepository;
		this.printingProperties = printingProperties; 
		this.epsonApiClient = epsonApiClient;
	}
	
	ObjectMapper objectMapper = new ObjectMapper();
    
	public PrinterSettingResDto createPrintJob(PrinterSettingReqDto settingReqDto) throws JsonProcessingException {
		
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

    public void uploadPrintFile(String uploadUri, byte[] fileData) {
        // 파일 업로드 로직 구현
        // WebClient를 사용하여 파일 업로드 요청을 보냄
    }

    public void executePrintJob(String deviceId, String jobId, String accessToken) {
        // 인쇄 작업 실행 로직 구현
        // WebClient를 사용하여 HTTP 요청을 보냄
    }





















}
