package com.bartosztanski.postsservice.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bartosztanski.postsservice.model.PostResponse;
import com.bartosztanski.postsservice.model.PostResponseCache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceCache {
	
	private final PostService postService;
	private PostResponseCache topPostsCache = new PostResponseCache();
	
	public List<PostResponse> getTopPosts() {
		
		Date date = new Date(System.currentTimeMillis()+1000*60*60);
		if (topPostsCache.getPosts().isEmpty()||topPostsCache.getUpdateTime().after(date)) {
			
		 	int pageNumber = 0;
			int pageSize = 5;
			int daysRange = 31;
			topPostsCache.setPosts(
				postService
				.getTopPosts(pageNumber, pageSize, daysRange)
				.stream()
				.map(e -> e.entityToResponse())
				.collect(Collectors.toList()));
			topPostsCache.setUpdateTime(new Date());
			log.info("updated topPostsCache");
		
		}
		log.info("Returned: "+ topPostsCache.getPosts().size()+" cached top posts");

		return topPostsCache.getPosts();
	 }
}
