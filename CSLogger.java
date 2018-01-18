import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CSLogger 
{
	private static String unique= TimeStamp.giveUniqueName();
	
	public static void writeCSEntry(String event)
	{
		PrintWriter writer;
		String path = "./";		
		try 
		{
			writer = new PrintWriter(new FileOutputStream(new File(path+"logs/CS_Info.txt"),true));			
			writer.append(TimeStamp.getTimeStamp()+" : "+event);			
			writer.println();			
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 		
		
	}
	
	public static void writeNodeEntry(String event)
	{
		PrintWriter writer;
		String path = "./";
		int node = SystemManager.getnodenumber();	
		try 
		{
			writer = new PrintWriter(new FileOutputStream(new File(path+"logs/Info_logs/node_"+node+"_info_"+unique+".txt"),true));			
			writer.append(TimeStamp.getTimeStamp()+" : "+event);			
			writer.println();			
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 		
		
	}
	
}
