package com.lumit.shop.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class TbAddress {
    private int addrId;
    private String addrName;
    private String receiver;
    private String phoneNumber;
    private int zipCd;
    private String baseAddr;
    private String detailAddr;
    private Timestamp regDt;
    private String userId;

    public static TbAddress createAddrByUser(TbLogin user) {
        TbAddress tbAddress = new TbAddress();
        tbAddress.setUserId(user.getUserId());
        tbAddress.setAddrName(String.format("%s님의 기본배송지", user.getName()));
        String[] addr = user.getAddress().split(", ");
        tbAddress.setZipCd(Integer.parseInt(addr[0]));
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < addr.length - 1; i++) {
            sb.append(addr[i]);
        }
        tbAddress.setBaseAddr(sb.toString());
        tbAddress.setDetailAddr(addr[addr.length - 1]);
        tbAddress.setReceiver(user.getName());
        tbAddress.setPhoneNumber(user.getPhone());
        return tbAddress;
    }
}
