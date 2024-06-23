package com.miml.epson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.miml.epson.entity.PrinterJob;
import com.miml.epson.entity.TokenInfo;
import com.miml.epson.repository.PrinterJobRepository;

@Service
public class PrinterJobService {

    @Autowired
    private PrinterJobRepository printerJobRepository;

    private WebClient webClient = WebClient.create();

    public PrinterJob createPrintJob(String deviceId, String accessToken) {
        // 인쇄 작업 생성 및 PrinterJob 저장
        // WebClient를 사용하여 HTTP 요청을 보냄
        String jobUri = "https://xxxx.xxxxx.xxx/api/1/printing/printers/" + deviceId + "/jobs";
        PrinterJob printerJob = new PrinterJob();
        // 예제 코드로 실제 요청을 생략합니다.
        printerJob.setJobId("exampleJobId");
        printerJob.setUploadUri("https://xxxx.xxxxx.xxx/uploadUri");
        printerJob.setFileName("SampleDoc.pdf");
        printerJob.setStatus("CREATED");

        printerJobRepository.save(printerJob);
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

	public TokenInfo authenticate(String deviceId) {
//		
//		String Authorization = "Basic " + printingProperties.getAuth();
//		Consumer<HttpHeaders> requestHeader = httpHeaders -> {
//            httpHeaders.add("Authorization", Authorization);
//            httpHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//        };
		// TODO Auto-generated method stub
		return null;
	}
}
