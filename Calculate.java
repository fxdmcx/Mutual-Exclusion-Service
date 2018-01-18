import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Calculate {
	
	
	public static List<String> getFileList(File file) {

	    List<String> result = new ArrayList<String>();

	    if (!file.isDirectory()) {
	        //System.out.println(file.getAbsolutePath());
	        result.add(file.getAbsolutePath());
	    } else {
	        File[] directoryList = file.listFiles(new FileFilter() {
	            public boolean accept(File file) {
	                if (file.isFile() && file.getName().indexOf("txt") > -1) {
	                    return true;
	                } else {
	                    return false;
	                }
	            }
	        });
	        for (int i = 0; i < directoryList.length; i++) {
	            result.add(directoryList[i].getPath());
	        }
	    }

	    return result;
	}
	public static String getTextFromTxt(String filePath) throws Exception {   

	    FileReader fr = new FileReader(filePath);   
	    BufferedReader br = new BufferedReader(fr);   
	    StringBuffer buff = new StringBuffer();   
	    String temp = null;   
	    while ((temp = br.readLine()) != null) {   
	        buff.append(temp + "\r\n");   
	    }   
	    br.close();   
	    return buff.toString();   
	}   
	
	public static long Runtime(String text) throws ParseException{
		long difference=0;
		String Count[]=text.split("\\n+");
    	int t= Count.length;
    	String a[] =Count[0].split(" : ");
    	String b[] =Count[t-1].split(" : ");
    	SimpleDateFormat df  =new SimpleDateFormat("MM/dd/yy, HH:mm:ss:SSS");
		difference=df.parse(b[0]).getTime()-df.parse(a[0]).getTime();
		return difference;
	}
	
	public static void Format(long d3){
		
		long day = d3 / (24 * 60 * 60 * 1000);
        long hour = (d3 / (60 * 60 * 1000) - day * 24);
        long min = ((d3 / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (d3 / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (d3 - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                - min * 60 * 1000 - s * 1000);
        
        System.out.println( ms
                + " ms");
	}
   public static void Format2(long d3){
		
		long day = d3 / (24 * 60 * 60 * 1000);
        long hour = (d3 / (60 * 60 * 1000) - day * 24);
        long min = ((d3 / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (d3 / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (d3 - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                - min * 60 * 1000 - s * 1000);
        
        System.out.println( (float)100/ms
                + " ms");
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			String path = "/H:/AOS/Critical_Section/Info_logs";	
			int count=0;
			long difference=0;
			long d3 =0;
		
			 File f = new File(path);
			    List<String> list = new ArrayList<String>();
			    list = getFileList(f);
			    String text=null;
			    for (String l : list) {
			    	text=getTextFromTxt(l);
			    	difference= difference+Runtime(text);
			    	String Count[]=text.split("\\n+");
			    	
			    	
			    	for(int i=0;i<Count.length;i++){
			    		
			    		if(Count[i].contains("and requesting")){
			    			
			    			String a[] =Count[i].split(" : ");
			    			SimpleDateFormat df  =new SimpleDateFormat("MM/dd/yy, HH:mm:ss:SSS");
			    			d3=d3 - df.parse(a[0]).getTime();			    			
			    			
			    		}else if(Count[i].contains("Exiting")){		    			
			    			String b[]=Count[i].split(" : ");
			    			SimpleDateFormat df  =new SimpleDateFormat("MM/dd/yy, HH:mm:ss:SSS");
			    			d3=d3 + df.parse(b[0]).getTime();
			    			    			
			    		}else if(Count[i].contains("message REQ_TOKEN")||Count[i].contains("message GRANT_TOKEN")){
			    			count++;
			    		}
			    	
			    	
			    	}
			    	
			    }  
			    
			    System.out.println("Message Complexity is "+count);
			   
			    System.out.println("Response Time ");
			    Format(d3/100);
			    System.out.println("System through");
			    Format2(difference);
		        
	}
}
