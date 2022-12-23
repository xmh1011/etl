import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// convert a RFC3339 timestamp to a UnixNano timestamp
public class convertRFC3339ToUnixNano {
    public static void main(String[] args) throws ParseException {
        String rfc3339 = "2019-01-01T00:00:00.000Z";// RFC3339 timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // RFC3339 format
        Date date = sdf.parse(rfc3339); // convert RFC3339 to Date
        long unixNano = date.getTime() * 1000000; // convert to UnixNano timestamp
        System.out.println(unixNano); // print UnixNano timestamp
    }
}

