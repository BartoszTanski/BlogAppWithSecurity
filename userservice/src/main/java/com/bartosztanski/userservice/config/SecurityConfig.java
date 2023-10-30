package com.bartosztanski.userservice.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final String[] DELETE_API_ENPOINTS = {"api/auth/posts/**","api/auth/video/**"};
	private final String[] POST_API_ENPOINTS = {"api/auth/posts**","api/auth/video**"};
	private final String[] PUT_API_ENPOINTS = {"api/auth/posts**"};
	
	@Value("${frontend.url}")
	private String frontendUrl;
 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(auth -> {auth
				.requestMatchers(HttpMethod.DELETE,DELETE_API_ENPOINTS)
				.authenticated()
				.requestMatchers(HttpMethod.PUT,PUT_API_ENPOINTS)
				.authenticated()
				.requestMatchers(HttpMethod.POST, POST_API_ENPOINTS)
				.authenticated()
				.requestMatchers("/mail/**")
				.authenticated()
				.anyRequest()
				.permitAll();
				})
			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	
	 @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(List.of(frontendUrl));
	        configuration.addAllowedHeader("*");
	        configuration.addAllowedMethod("*");
	        configuration.setAllowCredentials(true);
	        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
	        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
	        return urlBasedCorsConfigurationSource;
	    }
}
