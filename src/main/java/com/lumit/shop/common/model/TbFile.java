package com.lumit.shop.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TbFile{
	private String fileId;
	private String boardId;
	private String menuCd;
	private String fileNewName;
	private String fileName;
	private String fileSize; 
	private String filePath;
	private String fileType;
	private String fileExtension;
	private String regId;
	private String regDt;
	
	
	@Override
	public String toString() {
		return "TbFile [fileId=" + fileId + ", boardId=" + boardId + ", menuCd=" + menuCd + ", fileNewName="
				+ fileNewName + ", fileName=" + fileName + ", fileSize=" + fileSize + ", filePath=" + filePath
				+ ", fileType=" + fileType + ", fileExtension=" + fileExtension + ", regId=" + regId + ", regDt="
				+ regDt + "]";
	}
	
}
