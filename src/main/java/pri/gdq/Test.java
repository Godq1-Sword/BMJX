package pri.gdq;

import java.util.*;

/**
 * program : taxoa-backend
 * description:
 *
 * @author : gdq
 * data : 2019-12-03 11:28
 **/
public class Test {

    public static void main(String[] args) {
        Date date = new Date();
        java.sql.Date date2 = new java.sql.Date(date.getTime() - 24 * 3600 * 1000);
        System.out.println(date + "\n" + date2);
//        Date now = DateUtil.dateParse(new Date().toString(), "yyyy-MM-dd");
//        System.out.println(now);
//        for (int i = 0; i < 2; i++)
//            System.out.println(UUID.randomUUID().toString().replace("-", ""));
//        Date date = new Date(System.currentTimeMillis());
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String s1 = "1";
//        s1 += 2;
//        String s = dateFormat.format(date).substring(11);
//        System.out.println(s1);
//        java.sql.Date dateTime = new java.sql.Date(System.currentTimeMillis());
//        System.out.println(dateTime);
//
//        Integer i = new Integer(1);
//        System.out.println(i);
//        i = 128;
//        System.out.println(i);
    }
}
