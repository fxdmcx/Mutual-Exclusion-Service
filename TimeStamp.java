

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp 
{
	public static String getTimeStamp()
	{
		String returnVal;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss");
		Date date = new Date();		
		returnVal = dateFormat.format(date);
		return (returnVal+":"+System.currentTimeMillis()%1000);
	}
	@SuppressWarnings("deprecation")
	public static String giveUniqueName()
	{
		Date date = new Date();		
		return date.getMonth()+""+date.getDate()+""+date.getHours()+""+date.getMinutes()+""+date.getSeconds();
		
	}
}
