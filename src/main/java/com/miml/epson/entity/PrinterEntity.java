package com.miml.epson.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PrinterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;
    
    private String uploadUri;
    
    private String fileName;
    
    private String status;

    @Builder
	public PrinterEntity(Long id, String jobId, String uploadUri, String fileName, String status) {
		this.id = id;
		this.jobId = jobId;
		this.uploadUri = uploadUri;
		this.fileName = fileName;
		this.status = status;
	}
    
    // setter
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public void setUploadUri(String uploadUri) {
		this.uploadUri = uploadUri;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
