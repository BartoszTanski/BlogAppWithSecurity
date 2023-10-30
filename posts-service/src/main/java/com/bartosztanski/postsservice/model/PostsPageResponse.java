package com.bartosztanski.postsservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostsPageResponse {
	private int lp;
	private List<PostResponse> posts;
	private int size;
}
