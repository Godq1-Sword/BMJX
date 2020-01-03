package com.gdq.domain;

/**
 * program: HighSpeedScanner
 * description: 身份证信息
 *
 * @author : gdq
 * data : 2019-11-08 11:30
 **/
public class IDCard {
    // 姓名
    private String name = "";
    // 年龄
    private String sex = "";
    // 民族
    private String nation = "";
    // 生日
    private String birthday = "";
    // 地址
    private String address = "";
    // 证件号
    private String idcardno = "";
    // 身份证图片
    private byte[] wlt;

    static final private String[] nations = {
            "解码错", "汉", "蒙古", "回", "藏",
            "维吾尔", "苗", "彝", "壮", "布依",
            "朝鲜", "满", "侗", "瑶", "白",
            "土家", "哈尼", "哈萨克", "傣", "黎",
            "傈僳", "佤", "畲", "高山", "拉祜",
            "水", "东乡", "纳西", "景颇", "柯尔克孜",
            "土", "达斡尔", "仫佬", "羌", "布朗",
            "撒拉", "毛南", "仡佬", "锡伯", "阿昌",
            "普米", "塔吉克", "怒", "乌孜别克", "俄罗斯",
            "鄂温克", "德昴", "保安", "裕固", "京",
            "塔塔尔", "独龙", "鄂伦春", "赫哲", "门巴",
            "珞巴", "基诺", "编码错", "其他", "外国血统"
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public String getNationName(String nation) {
        int nationcode = Integer.parseInt(nation);
        if (nationcode >= 1 && nationcode <= 56) {
            this.nation = nations[nationcode];
        } else if (nationcode == 97) {
            this.nation = "其他";
        } else if (nationcode == 98) {
            this.nation = "外国血统";
        } else {
            this.nation = "编码错";
        }

        return this.nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIDCardNo() {
        return idcardno;
    }

    public void setIDCardNo(String idcardno) {
        this.idcardno = idcardno;
    }

    public void setGrantDept() {
    }

    public byte[] getWlt() {
        return this.wlt;
    }

    public void setWlt(byte[] wlt) {
        this.wlt = wlt;
    }
}
