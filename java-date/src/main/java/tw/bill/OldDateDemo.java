package tw.bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class OldDateDemo {
    public static void main(String[] args) throws ParseException {

        // date store the epoch
        // use new Date() get current time
        Date today = new Date();
        // epoch ms since 1970/01/01 00:00:00 UTC
        System.out.println(today.getTime());
        today.setTime(0);
        System.out.println(today);

        // date => calendar, calendar to calcuate or get info
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(today);
        System.out.println(calendar1);
        System.out.println(calendar1.getTime());

        Calendar calendar2 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar2.set(2018, Calendar.APRIL, 19, 22, 9, 10);
        calendar2.add(Calendar.MONTH, 1);
        System.out.println(calendar2);
        System.out.println(calendar2.get(Calendar.YEAR));
        System.out.println(calendar2.getTime());

        // date => string or string => date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(sdf.format(today));

        Date parsedDate = sdf.parse("01-01-2017");
        System.out.println(parsedDate);

    }
}
