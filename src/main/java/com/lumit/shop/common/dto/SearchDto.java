package com.lumit.shop.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchDto{
	private String title;
	private String content; 
	private String categories;
	
}
