package com.safeHer.sos_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safeHer.sos_service.dto.SosRequest;
import com.safeHer.sos_service.dto.SosResponse;
import com.safeHer.sos_service.entity.SosEvent;
import com.safeHer.sos_service.security.JwtUtil;
import com.safeHer.sos_service.service.SosService;

@RestController
@RequestMapping("/sos")
public class SosController {

	@Autowired
	private SosService sosService;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@GetMapping("/health")
	public ResponseEntity<String> health(){
		return ResponseEntity.ok("SOS service running on port 8082!");
	}
	
	
	@PostMapping("/trigger")
	public ResponseEntity<SosResponse> trigger(@RequestHeader("Authorization") String authHeader, @RequestBody SosRequest request){
		String token = authHeader.substring(7);
		Long userId = jwtUtil.extractUserId(token);
		String email = jwtUtil.extractEmail(token);
		
		SosResponse response =  sosService.triggerSos(userId, email, request.getLatitude(), request.getLongitude());
		return ResponseEntity.ok(response);
	}
	@PatchMapping("/{id}/accept")
	public ResponseEntity<SosResponse> accept(@PathVariable Long id, @RequestHeader ("Authorization") String authHeader){
		
		String token = authHeader.substring(7);
		Long helperId = jwtUtil.extractUserId(token);
		String helperEmail = jwtUtil.extractEmail(token);
		
		
		SosResponse response = sosService.acceptSos(id, helperId, helperEmail);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/active")
	public ResponseEntity<List<SosEvent>> getActive(){
		return ResponseEntity.ok(sosService.getAllActive());
	}
	
	@GetMapping("/my-history")
	public ResponseEntity<List<SosEvent>> myHistory(@RequestHeader("Authorization") String authHeader){
		String token = authHeader.substring(7);
		Long userId = jwtUtil.extractUserId(token);
		
		return ResponseEntity.ok(sosService.getMyHistory(userId));
	}
	
}
