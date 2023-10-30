package com.bartosztanski.userservice.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bartosztanski.userservice.error.InvalidJwtException;
import com.bartosztanski.userservice.error.PasswordTooShortException;
import com.bartosztanski.userservice.error.UserAlreadyExistsException;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
	
	private final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(
			@RequestBody RegisterRequest request) throws UserAlreadyExistsException, MessagingException {
		return ResponseEntity.ok(service.register(request));
	}
		
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request) {
		
	return ResponseEntity.ok(service.authenticate(request));
	}
	
	@PostMapping("/authenticated/getToken")
	public ResponseEntity<AuthenticationResponse> authenticatedOauth2(
			@RequestBody AuthenticationTokenRequest authTokenRequest) {
		
	return ResponseEntity.ok(service.getTokenForAuthenticatedUser(authTokenRequest));
	}
	
	@GetMapping("/sendChangePasswordEmail/{email}")
	public ResponseEntity<String> sendChangePasswordEmail(
			@PathVariable("email") String email) throws MessagingException {
			
		service.sendChangePasswordEmail(email);
		log.info("Reset password email send to: "+email);
		return ResponseEntity.ok("email send");
	}
	
	@PutMapping("/changePassword/{token}")
	public ResponseEntity<String> changePassword(
			@PathVariable("token") String token,
			@RequestParam("password") String password) throws InvalidJwtException, PasswordTooShortException {
			
		if (token==""||token==null) throw new InvalidJwtException("Lack of email verification token.");
		if (password.length()<7) throw new PasswordTooShortException(
				"Given password is to short: "+password.length()+" characters");
		
		String user = service.changePassword(token,password);
		log.info("Password of user: "+user+" changed");
		return ResponseEntity.ok("Password changed");
	}
	
	@GetMapping("/verifyRegistration/{token}")
	public ResponseEntity<String> verifyRegistration(
			@PathVariable("token") String token) throws InvalidJwtException {
			
		if (token==""||token==null) throw new InvalidJwtException("Lack of email verification token.");
		String user = service.verifyRegistration(token);
		log.info("User: "+user+" verified his email");
		return ResponseEntity.ok("Email verified succesfully");
	}
	
	@GetMapping("/hello") 
	String hello() {
		return "hello";
	}
}
