package com.safeHer.sos_service.dto;

import java.time.LocalDateTime;

import com.safeHer.sos_service.entity.SosEvent.SosStatus;

public class SosResponse {

	
	private Long sosId;
	private SosStatus status;
	private Double latitude;
	private Double longitude;
	private String message;
	private LocalDateTime triggeredAt;
	public SosResponse(Long sosId, SosStatus status, Double latitude, Double longitude, String message,
			LocalDateTime triggeredAt) {
		super();
		this.sosId = sosId;
		this.status = status;
		this.latitude = latitude;
		this.longitude = longitude;
		this.message = message;
		this.triggeredAt = triggeredAt;
	}
	public Long getSosId() {
		return sosId;
	}
	public void setSosId(Long sosId) {
		this.sosId = sosId;
	}
	public SosStatus getStatus() {
		return status;
	}
	public void setStatus(SosStatus status) {
		this.status = status;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTriggeredAt() {
		return triggeredAt;
	}
	public void setTriggeredAt(LocalDateTime triggeredAt) {
		this.triggeredAt = triggeredAt;
	}
	
	
}
