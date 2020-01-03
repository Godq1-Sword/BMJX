package com.gdq.util;

import com.gdq.domain.IDCard;

import java.nio.charset.StandardCharsets;

/**
 * program : HighSpeedScanner
 * description: 身份证读卡器工具类
 *
 * @author : gdq
 * data : 2019-12-16 12:59
 **/
public class IDCardUtil {

    public static int initDriver() {
        return ReaderAPI.instanceDll.initDriver(null);
    }

    public static IDCard getIdcardInfo(boolean sameCardReadOnce) {
        System.out.println("身份证信息正在读取");
        short wltlen = 1024;
        byte[] basemsg = new byte[256 + 1024];
        byte[] errmsg = new byte[256];
        int flag = sameCardReadOnce ? 1 : 0;
        int ret = ReaderAPI.instanceDll.ReadRawInfo(basemsg, errmsg, flag);
        IDCard idcard = new IDCard();
        if (ret != 0) {
            return null;
        }
        try {
            int varLen = 0;
            try {
                byte[] name = new byte[30];
                System.arraycopy(basemsg, varLen, name, 0, name.length);
                idcard.setName(new String(name, StandardCharsets.UTF_16LE).trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                byte[] sex = new byte[2];
                System.arraycopy(basemsg, varLen + 30, sex, 0, sex.length);
                idcard.setSex(new String(sex, StandardCharsets.UTF_16LE));
                if (idcard.getSex().equalsIgnoreCase("1"))
                    idcard.setSex("男");
                else
                    idcard.setSex("女");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                byte[] nation = new byte[4];
                System.arraycopy(basemsg, varLen + 32, nation, 0, nation.length);
                idcard.setNation(idcard.getNationName(new String(nation, StandardCharsets.UTF_16LE)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                byte[] birthday = new byte[16];
                System.arraycopy(basemsg, varLen + 36, birthday, 0, birthday.length);
                idcard.setBirthday(new String(birthday, StandardCharsets.UTF_16LE));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                byte[] address = new byte[70];
                System.arraycopy(basemsg, varLen + 52, address, 0, address.length);
                idcard.setAddress(new String(address, StandardCharsets.UTF_16LE).trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                byte[] idcardno = new byte[36];
                System.arraycopy(basemsg, varLen + 122, idcardno, 0, idcardno.length);
                idcard.setIDCardNo(new String(idcardno, StandardCharsets.UTF_16LE));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.arraycopy(basemsg, varLen + 158, new byte[30], 0, new byte[30].length);
                idcard.setGrantDept();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                byte[] wlt = new byte[wltlen];
                System.arraycopy(basemsg, 256, wlt, 0, wltlen);
                idcard.setWlt(wlt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("身份证信息读取成功");
        return idcard;
    }
}

