package com.lumit.shop.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TbMenu implements GrantedAuthority {
    private String menuCd;
    private String menuGroupCd;
    private String menuName;
    private String menuUrl;
    private String menuDefaultUrl;
    private String tmplCd;
    private String mainYn;
    private boolean isAdmin;
    private String iconHtml;

    @Override
    public String getAuthority() {
        return menuUrl;
    }

}