package com.lumit.shop.common.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TbProduct {
	private String productId;
	private String productName;
	private String productCd;
	private String price;
	private String disPrice;
	private String disRate;
	private String content; 
	private String summation;
	private String stocks;
	private String sales;
	private String dpStatus;
	private String status;
	private String watt;
	private String stock;
	private String deliveryFee;
	private String FreeDeliveryFee;
	private String delYn;
	private String regId;
	private String regDt;
	private String modId;
	private String modDt;
	
	// TbFile
	private String filePath;
	private String fileNewName;
	
	private List<String> colorIdList = new ArrayList<>();
	private List<String> tagIdList = new ArrayList<>();
	private String jsonFilesList;
	private String newJsonFilesList;
	private List<String> cateIdList = new ArrayList<>();
	private List<TbProductOption> oriOptionList = new ArrayList<>();
	
}
