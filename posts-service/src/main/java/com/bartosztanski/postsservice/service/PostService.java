package com.bartosztanski.postsservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bartosztanski.postsservice.entity.PostEntity;
import com.bartosztanski.postsservice.error.PostInsertFailedException;
import com.bartosztanski.postsservice.error.PostNotFoundExcepction;
import com.bartosztanski.postsservice.model.PostRequest;
import com.bartosztanski.postsservice.model.PostResponse;
import com.bartosztanski.postsservice.model.PostsPage;

@Service
public interface PostService {
	
	public String addPost(PostRequest postRequest) 
			throws PostInsertFailedException, IOException; 
	
	public void updatePost(String id, PostRequest postRequest)
			throws PostNotFoundExcepction; 
	
	public void deleteById(String id) throws PostNotFoundExcepction;
	public PostResponse getPostById(String id) throws PostNotFoundExcepction;
	public byte[] getImage(String id) throws PostNotFoundExcepction; 
	public void updateLikes(String id, int i) throws PostNotFoundExcepction;
	public List<PostEntity> getlAllPosts();
	public List<PostEntity> getPostsByDate(int days); 
	public List<PostEntity> getTopPosts(int page, int limit, int days);
	public List<PostEntity> getPostsByTag(String tagId);
	public List<PostEntity> findPostByRegexpTitle(String regexp);
	public PostsPage getlAllPostsByPage(int lp, int size);
}