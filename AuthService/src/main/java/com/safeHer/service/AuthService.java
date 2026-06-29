package com.safeHer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;
import com.safeHer.entity.User;
import com.safeHer.repository.UserRepository;
import com.safeHer.security.JwtUtil;

@Service
public class AuthService {

	 @Autowired
	 private UserRepository userRepository;
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 @Autowired
	 private JwtUtil jwtUtil;
	 
	 
	 public String register(String name, String email, String phone , String password) {
		 if(userRepository.existsByEmail(email)) {
			 return "email already registered";
		 }
		 
		 if (userRepository.existsByPhone(phone)) {
			 return "phone alredy registered";
		 }
		 
		 User user = new User();
		 user.setName(name);
		 user.setEmail(email);
		 user.setPhone(phone);
		 user.setPassword(passwordEncoder.encode(password));
		 user.setRole(User.Role.USER);
		 user.setActive(true);
		 
		 userRepository.save(user);
		 
		 String token = jwtUtil.generateToken(user.getEmail(),user.getId(),user.getRole().name());
		 
		 return token;
	 }
	 
	 public String login(String email, String password) {
		 
		 Optional<User> optionalUser =  userRepository.findByEmail(email);
		 
		 if (optionalUser.isEmpty()) {
			 return "wrong credential";
		 }
		 
		 User user = optionalUser.get();
		 
		 if (!user.isActive()) {
			 return "deactivated account";
		 }
		 
		 if (!passwordEncoder.matches(password, user.getPassword())) {
			 return "wrong password ";
		 }
		 
		 String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole().name());
		 
		 return token;
	 }
	 
}
