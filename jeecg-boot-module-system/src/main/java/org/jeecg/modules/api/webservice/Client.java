package org.jeecg.modules.api.webservice;


import org.jeecg.common.util.DateUtils;

import java.util.Date;

public class Client {
    public static void main(String args[]) throws Exception {
        System.out.println(new Date().toString());
 /*       List<User> users = new ArrayList<>();
        User user = new User();
        user.setAge("1");
        users.add(user);
        user.setName("5454");
        users.add(user);
*/
        /*java.sql.Date dt=StringToDate("2010-10-20");
        System.out.println(new Timestamp(dt.getTime()).getTime());
        System.out.println(dt.getClass().getName());
        System.out.println(dt);
       System.out.println(dt.toString());*/
      /*  System.out.println(new Date().getTime());*/
        String test="121319";
        System.out.println(test.substring(1,test.length()));
    }
    public static java.sql.Date StringToDate(String sDate) {
        /**
         *str转date方法
         */
        String str = sDate;
        java.util.Date d = null;
        try {
            d = DateUtils.date_sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(d.getTime());
        return date;
    }
}
