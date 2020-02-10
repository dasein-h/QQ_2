package client;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NowTime {
    //获取当前时间
    public static String getTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return format.format(calendar.getTime());
    }

    //调试使用
    public static void main(String[] args) {
        System.out.println(new NowTime().getTime());
    }
}
