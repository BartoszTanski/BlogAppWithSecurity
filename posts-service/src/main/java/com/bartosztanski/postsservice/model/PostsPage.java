package com.bartosztanski.postsservice.model;

import java.util.List;

import com.bartosztanski.postsservice.entity.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostsPage {
	private int lp;
	private List<PostEntity> posts;
	private int size;
}
