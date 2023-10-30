package com.bartosztanski.postsservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bartosztanski.postsservice.entity.VideoFilesEntity;

public interface VideoFilesRepository extends MongoRepository<VideoFilesEntity, String> {
	
	List<VideoFilesEntity> findAll();
}
