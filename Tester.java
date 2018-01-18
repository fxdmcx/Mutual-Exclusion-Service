import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;


public class Tester extends BufferedReader {
	
    Set<String> lines = new HashSet<String>();

    public Tester(Reader arg0) {
        super(arg0);
    }
 
    @SuppressWarnings("resource")
	public static void main(String args[]) {
        try {
        	
            // Open the file that is the first command line parameter
        	String path = "./logs/CS_Info.txt";
            FileInputStream fstream = new FileInputStream(path);
            Tester br = new Tester(new InputStreamReader(fstream));
           
            String currentnode1,currentnode2;
            String strLine;
            String enter = "Entering";
            String exit = "Exiting";
            
            int count=0;
            
            // Read File Line By Line
            String[] nodelist;
            
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
               //     System.out.println(strLine);
           
            
            	count++;
                
        
            	
                if(strLine.contains(enter)){
                	
                	nodelist = strLine.split(" ");
                	currentnode1 = nodelist[6];
       
                	 strLine = br.readLine();
            	     count++;
       
            	    
            	   if(strLine.contains(exit)){
            		   nodelist = strLine.split(" ");
            		   currentnode2 = nodelist[6];
       
            		   
            		   if(currentnode1.equals(currentnode2)){
            			   strLine = br.readLine();
                		   count++;
                		  continue;	
            		   }
            			   else{
            			   System.out.println("Error!!!!. Check the input file at line number:"+count);
                		   System.exit(1);
            			   }
            		  
            	    }
            	   
            	   else{
            		   System.out.println("Error. Check the input file at line number:"+count);
            		   System.exit(1);
            		 }
            
                  }
                else{
         		   System.out.println("Error!!. Check the input file at line number:"+count);
         		   System.exit(1);
         		 }
            
            }
           
            // Close the input stream
	    System.out.println("Number of Lines Read:"+count);
            System.out.println("File is correct");
               fstream.close();
        
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }
}
