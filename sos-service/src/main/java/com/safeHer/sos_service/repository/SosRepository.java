package com.safeHer.sos_service.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safeHer.sos_service.entity.SosEvent;
import com.safeHer.sos_service.entity.SosEvent.SosStatus;

public interface SosRepository extends JpaRepository<SosEvent, Long> {

	List<SosEvent> findByUserId(Long userId);
	List<SosEvent> findByStatus(SosStatus status);
	List<SosEvent> findByUserIdAndStatus(Long userId, SosStatus status);
}
