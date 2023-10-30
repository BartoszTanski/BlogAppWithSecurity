package com.bartosztanski.postsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.bartosztanski.postsservice.repository.PostRepository;
import com.bartosztanski.postsservice.repository.VideoFilesRepository;
import com.bartosztanski.userservice.user.UserRepository;

@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableMongoRepositories(basePackageClasses = {PostRepository.class, VideoFilesRepository.class})
@SpringBootApplication
@ComponentScan(basePackages = {"com.bartosztanski*"})
public class BlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
		System.out.println("Application started successfully!");

	}

}
