package com.lumit.shop.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TbMapping{
	private String optionId;
	
	private String productId;
	
	private String categoryId;
	private String categoryName;
	private String parent;
	
	private String colorName;
	private String colorCd;
	
	private String tagName;
}
