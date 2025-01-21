package com.lumit.shop.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommonSearch{
	private String useYn;
	private String grpCd;
	private String strDt;
	private String endDt;
	private String optionName;
	private String optionDvCd;
	private String optionCd;
	
	private String productName;
	private String productCd;
	private String status;
	private String dpStatus;
	
	private int cntPerPage = 10;
}
