package com.lumit.shop.common.model;

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

	@Override
	public String toString() {
		return "TbBoard [boardId=" + boardId + ", menuCd=" + menuCd + ", menuDvCd=" + menuDvCd + ", title=" + title
				+ ", content=" + content + ", password=" + password + ", topFix=" + topFix + ", useYn=" + useYn
				+ ", delYn=" + delYn + ", rplyYn=" + rplyYn + ", fileYn=" + fileYn + ", viewCount=" + viewCount
				+ ", regId=" + regId + ", regDt=" + regDt + ", modId=" + modId + ", modDt=" + modDt + "]";
	}
	
	
	
}
