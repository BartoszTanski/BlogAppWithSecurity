package com.bartosztanski.postsservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseCache {
	private Date updateTime = new Date();
	private List<PostResponse> posts = new ArrayList<>();
}
