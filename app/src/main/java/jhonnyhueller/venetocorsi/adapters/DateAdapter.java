package jhonnyhueller.venetocorsi.adapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jhonny
 */
public class DateAdapter {
    public static final String DATABASE_FORMAT ="yyyy-MM-dd HH:mm:ss.SSS";
    public static final String XML_COURSE_DATE_FORMAT ="yyyy-MM-dd HH:mm:ss";

    public static String formatDbFromDate(Date data){
        DateFormat format=new SimpleDateFormat(DATABASE_FORMAT, Locale.getDefault());
        return format.format(data);
    }
    public static Date dateJFromFormatDb(String s) throws ParseException {
        DateFormat format=new SimpleDateFormat(DATABASE_FORMAT, Locale.getDefault());
        return format.parse(s);
    }
    public static Date dateJFromFormatXml(String s) throws ParseException {
        DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        return format.parse(s);
    }
    public static String formatShortStringFromDate(Date date){
        DateFormat dateFormat=new SimpleDateFormat("E dd MMM", Locale.getDefault());
        return dateFormat.format(date);
    }
}
