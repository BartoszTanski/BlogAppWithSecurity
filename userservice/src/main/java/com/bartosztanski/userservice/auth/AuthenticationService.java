package com.bartosztanski.userservice.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bartosztanski.notificationservice.model.TemplateMailRequest;
import com.bartosztanski.notificationservice.service.EmailService;
import com.bartosztanski.userservice.config.JwtService;
import com.bartosztanski.userservice.error.UserAlreadyExistsException;
import com.bartosztanski.userservice.user.Role;
import com.bartosztanski.userservice.user.User;
import com.bartosztanski.userservice.user.UserRepository;
import com.bartosztanski.userservice.user.UserResponse;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
	
	@Value("${blog.client.secret}")
	private String BLOG_CLIENT_SECRET;
	@Value("${blog.user.default.password}")
	private String BLOG_USER_DEFAULT_PASSWORD;
	@Value("${frontend.url}")
	private String frontendUrl;
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final EmailService emailService;
	private final AuthenticationManager authenticationManager;
	
	
	public String register(RegisterRequest request) throws UserAlreadyExistsException, MessagingException {
		
		if (repository.existsByEmailIgnoreCase(request.getEmail())) 
			throw new UserAlreadyExistsException("User with this email already exists");
		
		var user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.profilePic(request.getProfilePic())
				.role(Role.USER)
				.enabled(false)
				.build();
		repository.save(user);
		
		sendVerifyRegistrationEmail(user);
		log.info("New user registered: "+request.getEmail());
		return "User registration successfull";
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()));
		
		var user = repository.findByEmailIgnoreCase(request.getEmail()).orElseThrow();
		
		UserResponse userResponse = UserResponse.builder()
				.id(new Random().nextInt(10000)+"")
				.name(user.getFirstName()+" "+user.getLastName())
				.email(user.getEmail())
				.image(user.getProfilePic())
				.build();
		
		Map<String,Object> map = new HashMap<>();
		map.put("user", userResponse);
		var jwtToken = jwtService.generateToken(map, user);
		
		log.info("Generated new EMAIL/PASS token for  user: "+ request.getEmail());
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse getTokenForAuthenticatedUser(AuthenticationTokenRequest authtokenRequest) {

		if (!authtokenRequest.getSecret().equalsIgnoreCase(BLOG_CLIENT_SECRET))
			throw new BadCredentialsException("Client secret invalid");
		
		var user = repository.findByEmailIgnoreCase(authtokenRequest.getEmail()).orElseGet(() -> {
			var _user = User.builder() //if user is not in db we save him (user is already authenticated - by us or Oauth2 providers)
				.firstName("Name")
				.lastName("Lastname")
				.email(authtokenRequest.getEmail())
				.password(passwordEncoder.encode(BLOG_USER_DEFAULT_PASSWORD))
				.role(Role.USER)
				.build();
			
			repository.save(_user);
			return _user; 
			});
		
		var jwtToken = jwtService.generateToken(user);
		log.info("Generated new token for authenticated user: "+ authtokenRequest.getEmail());
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public void sendChangePasswordEmail(String email) throws MessagingException {
		
		var user = repository.findByEmailIgnoreCase(email).orElseThrow(
				()-> new NoSuchElementException("User with this email doesnt exist"));
		
		var jwtToken = jwtService.generateToken(new HashMap<>(), user, 1000*60*10);
		log.info("Generated new RESET PASSWORD token for  user: "+ email);
		
		Map<String, Object> map = new HashMap<>();
		map.put("recipientName", user.getFirstName());
		map.put("link",frontendUrl+"/resetPassword?token="+jwtToken);
		map.put("senderName","UserVerifier");
		
		TemplateMailRequest mailRequest = TemplateMailRequest
				.builder()
				.to(email)
				.subject("Reset your password")
				.templateName("reset-password-link")
				.variablesMap(map)
				.attachment(null)
				.build();
		
		emailService.sendTemplateMessage(mailRequest);
	}
	
	public void sendVerifyRegistrationEmail(User user) throws MessagingException {
		
		var jwtToken = jwtService.generateToken(new HashMap<>(), user, 1000*60*10);
		log.info("Generated new VERIFY REGISTRATION token for  user: "+ user.getEmail());
		
		Map<String, Object> map = new HashMap<>();
		map.put("recipientName", user.getFirstName());
		map.put("link",frontendUrl+"/verifyRegistration?token="+jwtToken);
		map.put("senderName","UserVerifier");
		TemplateMailRequest mailRequest = TemplateMailRequest
						.builder()
						.to(user.getEmail())
						.subject("Confirm your email")
						.templateName("registration-link")
						.variablesMap(map)
						.attachment(null)
						.build();
		
		emailService.sendTemplateMessage(mailRequest);
	}

	public String changePassword(String token, String password) {
		String email = jwtService.extractUsername(token);
		User user = repository.findByEmailIgnoreCase(email).orElseThrow(
				()-> new NoSuchElementException("User with this email dont exists"));
		if (jwtService.isTokenValid(token, user)) {
			user.setPassword(passwordEncoder.encode(password));
			repository.save(user);
		}	
		
		return email;
	}

	public String verifyRegistration(String token) {
		String email = jwtService.extractUsername(token);
		User user = repository.findByEmailIgnoreCase(email).orElseThrow(
				()-> new NoSuchElementException("User with this email dont exists"));
		if (jwtService.isTokenValid(token, user)) {
			if(!user.isEnabled()) {
				user.setEnabled(true);
				repository.save(user);
			}
			
		}	
		
		
		return email;
	}
	
	
}
