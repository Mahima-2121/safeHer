package com.safeHer.sos_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "sos_events")
public class SosEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private String userEmail;
	private Double latitude;
	private Double longitude;
	
	@Enumerated(EnumType.STRING)
	private SosStatus status;
	
	private Long acceptedByUserId;
	private String acceptedByEmail;
	
	private LocalDateTime triggeredAt;
	private LocalDateTime acceptedAt;
	private LocalDateTime resolvedAt;
	
	@PrePersist
	public void onCreate() {
		this.triggeredAt=LocalDateTime.now();
		this.status= SosStatus.ACTIVE;
	}
	
	public enum SosStatus{
		ACTIVE,
		ACCEPTED,
		RESOLVED,
		ESCALATED
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public SosStatus getStatus() {
		return status;
	}

	public void setStatus(SosStatus status) {
		this.status = status;
	}

	public Long getAcceptedByUserId() {
		return acceptedByUserId;
	}

	public void setAcceptedByUserId(Long acceptedByUserId) {
		this.acceptedByUserId = acceptedByUserId;
	}

	public String getAcceptedByEmail() {
		return acceptedByEmail;
	}

	public void setAcceptedByEmail(String acceptedByEmail) {
		this.acceptedByEmail = acceptedByEmail;
	}

	public LocalDateTime getTriggeredAt() {
		return triggeredAt;
	}

	public void setTriggeredAt(LocalDateTime triggeredAt) {
		this.triggeredAt = triggeredAt;
	}

	public LocalDateTime getAcceptedAt() {
		return acceptedAt;
	}

	public void setAcceptedAt(LocalDateTime acceptedAt) {
		this.acceptedAt = acceptedAt;
	}

	public LocalDateTime getResolvedAt() {
		return resolvedAt;
	}

	public void setResolvedAt(LocalDateTime resolvedAt) {
		this.resolvedAt = resolvedAt;
	}
	
	

}
