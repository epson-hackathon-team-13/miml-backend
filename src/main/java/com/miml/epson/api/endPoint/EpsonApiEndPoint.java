package com.miml.epson.api.endPoint;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.miml.epson.api.properties.PrintingProperties;

public class EpsonApiEndPoint {
	
	private final PrintingProperties printingProperties;
	
	public EpsonApiEndPoint(PrintingProperties printingProperties) {
		this.printingProperties = printingProperties;
	}
	
	public class PrintingAPIEndpoints {
	    public static final String AUTHENTICATION = "/api/1/printing/oauth2/auth/token?subject=printer";
	    public static final String REISSUE_ACCESS_TOKEN = "/api/1/printing/oauth2/auth/token?subject=printer";
	    public static final String GET_DEVICE_PRINT_CAPABILITIES = "/api/1/printing/printers/{device_id}/capability/{print_mode}";
	    public static final String PRINT_SETTING = "/api/1/printing/printers/{device_id}/jobs";
	    public static final String UPLOAD_PRINT_FILE = "{upload_uri}/File=1.{extension}";
	    public static final String EXECUTE_PRINT = "/api/1/printing/printers/{device_id}/jobs/{job_id}/print";
	    public static final String CANCEL_PRINT = "/api/1/printing/printers/{device_id}/jobs/{job_id}/cancel";
	    public static final String GET_PRINT_JOB_INFORMATION = "/api/1/printing/printers/{device_id}/jobs/{job_id}";
	    public static final String GET_DEVICE_INFORMATION = "/api/1/printing/printers/{device_id}";
	    public static final String CANCEL_AUTHENTICATION = "/api/1/printing/printers/{device_id}";
	    public static final String NOTIFICATION_SETTING = "/api/1/printing/printers/{device_id}/settings/notification";
	}

	public class ScanningAPIEndpoints {
	    public static final String GET_SCAN_DESTINATIONS_LIST = "/api/1/scanning/scanners/{device_id}/destinations";
	    public static final String REGISTER_SCAN_DESTINATION = "/api/1/scanning/scanners/{device_id}/destinations";
	    public static final String UPDATE_SCAN_DESTINATION = "/api/1/scanning/scanners/{device_id}/destinations";
	    public static final String DELETE_SCAN_DESTINATION = "/api/1/scanning/scanners/{device_id}/destinations";
	}
	
	 public String getApiEndPoint(
	            String host,
	            String endPoint,
	            String deviceId,
	            String jobId,
	            String printMode,
	            String uploadUri
	            ) {
		 if (host == null || endPoint == null) {
			 throw new IllegalArgumentException("Host and endPoint must not be null");
		 }

		 String requestPath = endPoint;
		 
		 if (deviceId != null) {
			 requestPath = requestPath.replace("{device_id}", deviceId);
		 }
		 if (jobId != null) {
			 requestPath = requestPath.replace("{job_id}", jobId);
		 }
		 if (printMode != null) {
			 requestPath = requestPath.replace("{print_mode}", printMode);
		 }
		 if (uploadUri != null) {
			 requestPath = requestPath.replace("{upload_uri}", uploadUri);
		 }
		 String requestUrl = "https://" + printingProperties.getHostName() + "/" + requestPath;
		 
		 return requestUrl;
	 }
	 
	 public String getAuth() {
		 String clientId = printingProperties.getClientId();
		 String secret = printingProperties.getSecret();
		 return Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes(StandardCharsets.UTF_8));
	 }

}
