package com.miml.epson.service;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.miml.epson.api.client.EpsonApiClient;
import com.miml.epson.api.endPoint.EpsonApiEndPoint;
import com.miml.epson.api.properties.PrintingProperties;
import com.miml.epson.dto.AuthenticationDto;
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
	

	public void authenticate(AuthenticationDto authenticationDto) {
		
		String host = printingProperties.getHostName();
		String endPoint = EpsonApiEndPoint.AUTHENTICATION;
		String url = "https://" + host + endPoint;
		String Authorization = "Basic " + printingProperties.getAuth();

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("grant_type", authenticationDto.getGrant_type());
        formData.add("username", authenticationDto.getUsername());
        formData.add("password", authenticationDto.getPassword());

		Consumer<HttpHeaders> requestHeader = httpHeaders -> {
		    httpHeaders.add("Authorization", Authorization);
		    httpHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		};
		
		TokenEntity tokenEntity = epsonApiClient.post(url, formData, TokenEntity.class, requestHeader);
		tokenEntity.setUsername(authenticationDto.getUsername());
		
		tokenRepository.save(tokenEntity);
		
	} 

    public PrinterEntity createPrintJob(String deviceId, String accessToken) {
        // 인쇄 작업 생성 및 PrinterJob 저장
        // WebClient를 사용하여 HTTP 요청을 보냄
        String jobUri = "https://xxxx.xxxxx.xxx/api/1/printing/printers/" + deviceId + "/jobs";
        PrinterEntity printerJob = new PrinterEntity();
        // 예제 코드로 실제 요청을 생략합니다.
        printerJob.setJobId("exampleJobId");
        printerJob.setUploadUri("https://xxxx.xxxxx.xxx/uploadUri");
        printerJob.setFileName("SampleDoc.pdf");
        printerJob.setStatus("CREATED");

//        printerJobRepository.save(printerJob);
        return printerJob;
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
