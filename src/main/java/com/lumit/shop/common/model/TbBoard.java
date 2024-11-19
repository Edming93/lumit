package com.lumit.shop.common.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TbBoard{
	private String boardId;
	private String menuCd;
	private String menuDvCd;
	private String categories;
	private String title;
	private String content; 
	private String password;
	private String topFix;
	private String useYn;
	private String delYn;
	private String rplyYn;
	private String fileYn;
	private String viewCount;
	private String regId;
	private String regDt;
	private String modId;
	private String modDt;
	
	private List<String> jsonFileList;

	@Override
	public String toString() {
		return "TbBoard [boardId=" + boardId + ", menuCd=" + menuCd + ", menuDvCd=" + menuDvCd + ", categories="
				+ categories + ", title=" + title + ", content=" + content + ", password=" + password + ", topFix="
				+ topFix + ", useYn=" + useYn + ", delYn=" + delYn + ", rplyYn=" + rplyYn + ", fileYn=" + fileYn
				+ ", viewCount=" + viewCount + ", regId=" + regId + ", regDt=" + regDt + ", modId=" + modId + ", modDt="
				+ modDt + ", jsonFileList=" + jsonFileList + "]";
	}


	
	
	
}
