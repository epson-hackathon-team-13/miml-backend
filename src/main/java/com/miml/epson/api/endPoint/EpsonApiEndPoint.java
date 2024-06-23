package com.miml.epson.api.endPoint;

import org.springframework.stereotype.Component;

@Component
public class EpsonApiEndPoint {
	
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

    public static final String GET_SCAN_DESTINATIONS_LIST = "/api/1/scanning/scanners/{device_id}/destinations";
    public static final String REGISTER_SCAN_DESTINATION = "/api/1/scanning/scanners/{device_id}/destinations";
    public static final String UPDATE_SCAN_DESTINATION = "/api/1/scanning/scanners/{device_id}/destinations";
    public static final String DELETE_SCAN_DESTINATION = "/api/1/scanning/scanners/{device_id}/destinations";	 

}
