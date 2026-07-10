package com.safeHer.sos_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.safeHer.sos_service.dto.SosResponse;
import com.safeHer.sos_service.entity.SosEvent;
import com.safeHer.sos_service.entity.SosEvent.SosStatus;
import com.safeHer.sos_service.repository.SosRepository;

@Service
public class SosService {

	@Autowired
	private SosRepository sosRepository;

	public SosResponse triggerSos(long userId, String email, Double latitude, Double longitude) {
		List<SosEvent> activeSos = sosRepository.findByUserIdAndStatus(userId, SosStatus.ACTIVE);

		if (!activeSos.isEmpty()) {
			SosEvent existing = activeSos.get(0);
			return new SosResponse(existing.getId(), existing.getStatus(), existing.getLatitude(),
					existing.getLongitude(), "Pehle se active SOS hai!", existing.getTriggeredAt());
		}

		SosEvent event = new SosEvent();
		event.setUserId(userId);
		event.setUserEmail(email);
		event.setLatitude(latitude);
		event.setLongitude(longitude);

		SosEvent saved = sosRepository.save(event);

		return new SosResponse(saved.getId(), saved.getStatus(), saved.getLatitude(), saved.getLongitude(),
				"sos sent succesfully", saved.getTriggeredAt());
	}

	public SosResponse acceptSos(Long sosId, Long helperId, String helperEmail) {

		SosEvent event = sosRepository.findById(sosId).orElseThrow(() -> new RuntimeException("their is not sos"));
		if (event.getStatus() != SosStatus.ACTIVE) {
			return new SosResponse(event.getId(), event.getStatus(), event.getLatitude(), event.getLongitude(),
					"aldreay taken", event.getTriggeredAt());
		}

		event.setStatus(SosStatus.ACCEPTED);
		event.setAcceptedByUserId(helperId);
		event.setAcceptedByEmail(helperEmail);
		event.setAcceptedAt(LocalDateTime.now());

		SosEvent updated = sosRepository.save(event);

		return new SosResponse(updated.getId(), updated.getStatus(), updated.getLatitude(), updated.getLongitude(),
				helperEmail + "is comming", updated.getTriggeredAt());
	}

	public SosResponse resolveSos(Long sosid) {
		SosEvent event = sosRepository.findById(sosid).orElseThrow(() -> new RuntimeException("their is no SOS"));
		event.setStatus(SosStatus.RESOLVED);
		event.setResolvedAt(LocalDateTime.now());

		SosEvent updated = sosRepository.save(event);

		return new SosResponse(updated.getId(), updated.getStatus(), updated.getLatitude(), updated.getLongitude(),
				"you are safe", updated.getTriggeredAt());
	}

	@Scheduled(fixedRate = 30000)
	public void checkUnacceptedSos() {
		List<SosEvent> activeSos = sosRepository.findByStatus(SosStatus.ACTIVE);

		LocalDateTime threeMinutesago = LocalDateTime.now().minusMinutes(3);

		for (SosEvent event : activeSos) {
			if (event.getTriggeredAt().isBefore(threeMinutesago)) {
				event.setStatus(SosStatus.ESCALATED);
				sosRepository.save(event);
				System.out.println("ESCLATED! SOS ID: " + event.getId() + " -- send alert to admin ! User: "
						+ event.getUserEmail());

			}
		}
	}

	public List<SosEvent> getAllActive() {
		return sosRepository.findByStatus(SosStatus.ACTIVE);
	}

	public List<SosEvent> getMyHistory(Long userId) {
		return sosRepository.findByUserId(userId);
	}

}
