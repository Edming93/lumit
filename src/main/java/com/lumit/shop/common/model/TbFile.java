package com.lumit.shop.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TbFile{
	private String fileId;
	private String pkId;
	private String fileDvCd;
	private String menuCd;
	private String fileNewName;
	private String fileName;
	private String fileSize; 
	private String filePath;
	private String fileType;
	private String fileExtension;
	private String regId;
	private String regDt;
	
}
