import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class Parser
{
	public static BufferedReader br = null;
	public static ArrayList<String> hosts = new ArrayList<String>();
	public static ArrayList<String> hostname = new ArrayList<String>(10);
	public static ArrayList<Integer> ports = new ArrayList<Integer>();
	
	public static double delayInRequests = 0.0;
	public static double durationInCS = 0.0;
	public static int noOfRequests = 0;
	
	// Function to collect the details about the topology and ports.
	public static void checkNodes()
	{
		try
		{
			boolean isFirstLine = true;
			String sCurrentLine; 				
			br = new BufferedReader(new FileReader("conf.txt")); 
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				sCurrentLine.trim();
				
				if (sCurrentLine.equals(""))
					continue;
				//only lines begin with an unsigned integer are considered valid
				if (sCurrentLine.charAt(0) < '0' || sCurrentLine.charAt(0) > '9')
					continue;
				
				
				if (isFirstLine)										//parse the first line
				{
					String[] part = sCurrentLine.split("\\s+");
					delayInRequests = Double.parseDouble(part[1]);		//mean value for inter-request delay
					durationInCS = Double.parseDouble(part[2]);			//mean value for cs-execution time
					noOfRequests = Integer.parseInt(part[3]);			//number of requests each node generate
					isFirstLine = false;
				}
				else
				{
					String[] parts = sCurrentLine.split("\\s+");
					String oneNode = parts[1];
					String hn = oneNode + ".utdallas.edu";
					int port = Integer.parseInt(parts[2]);
					if (hn.equals(InetAddress.getLocalHost().getHostName())) {
						int parent = Integer.parseInt(parts[3]);
						SystemManager.setParent(parent);
						//System.out.println(oneNode + "'s parent is: " + parent);
					}
					hostname.add(hn);
					ports.add(port);
				}	
				
			}		
			
			
			//Start listening to the port provided.
			//System.out.println(Sender.getnodenumber());
			for(int i=0;i<hostname.size();i++)
			{				
				if(hostname.get(i).trim().equals(InetAddress.getLocalHost().getHostName()))
				{
					hosts.add(hostname.get(i));					
					Receiver.setListen_port(ports.get(i));
					// Start the listener port to accept any incoming connections.					
					//System.out.println("Starting Tcp listener thread");
					Thread listener = new Thread(new Receiver());
					listener.start();
				}
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public static int getPort(int node)
	{		
		return ports.get(node);
	}
}
