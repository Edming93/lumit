package com.lumit.shop.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import groovy.transform.ToString;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbCode {
    private String grpCd;
    private String cd;
    private String grpCdNm;
    private String cdNm;
    private String useYn;
    private String ctt;
    private String regId;
    private String regDt;
    private String modId;
    private String modDt;
    
	@Override
	public String toString() {
		return "TbCode [grpCd=" + grpCd + ", cd=" + cd + ", grpCdNm=" + grpCdNm + ", cdNm=" + cdNm + ", useYn=" + useYn
				+ ", ctt=" + ctt + ", regId=" + regId + ", regDt=" + regDt + ", modId=" + modId + ", modDt=" + modDt
				+ "]";
	}

    

}