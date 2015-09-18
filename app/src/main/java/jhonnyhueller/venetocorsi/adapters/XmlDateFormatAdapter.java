package jhonnyhueller.venetocorsi.adapters;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by jhonny
 */
public class XmlDateFormatAdapter implements Transform<Date> {
    private final String TAG = getClass().getSimpleName();

    private DateFormat dateFormat;


    public XmlDateFormatAdapter(DateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
    }



    @Override
    public Date read(String value) throws Exception
    {
        return dateFormat.parse(value);
    }


    @Override
    public String write(Date value) throws Exception
    {
        return dateFormat.format(value);
    }

}
