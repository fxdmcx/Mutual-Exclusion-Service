import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class Sender {	
	
	private static Sender sync = new Sender();

	private static int process_node;
	private static String host;
	

	public static void nodeProcess()
	{
		try 
		{
			host = InetAddress.getLocalHost().getHostName();
			int itr = 0;
			for (String h : Parser.hostname) 
			{				
				if (h.equalsIgnoreCase(host)) 
				{
					process_node = itr;
					
					System.out.println("Process node: "+process_node +", listening on port: "+Parser.getPort(process_node));
				}
				
				itr++;
			}
		}
		catch (UnknownHostException e)
		{
			System.out.println("Failed : Host name");
			e.printStackTrace();
		}
	
	}

	public static void doWait()
	{
		synchronized (sync) {
			try {
				sync.wait();
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
	}

	public static void doNotify() {
		synchronized (sync) {
			sync.notify();
		}
	}
	
	public static void requestToken(int node, int port)
	{
				
		Socket srv_socket;
		try {
			CSLogger.writeNodeEntry("Node: "+ Sender.getnodenumber()+" REQ_TOKEN from node: "+process_node+" to node "+node+" : "+SystemManager.childQueue());
			//System.out.println(TimeStamp.getTimeStamp()+" : "+"Node: "+ Sender.getnodenumber()+" REQ_TOKEN from node: "+process_node+" to node "+node);
			srv_socket = new Socket(Parser.hostname.get(node), port);
			PrintWriter msg_send = new PrintWriter(
					srv_socket.getOutputStream(), true);
			msg_send.println("REQ_TOKEN from node: "+process_node);			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void grantToken(int node, int port)
	{
				
		Socket srv_socket;
		try {
			CSLogger.writeNodeEntry("Node: "+ Sender.getnodenumber()+" GRANT_TOKEN from node: "+process_node+" to node "+node+" : "+SystemManager.childQueue());
			//System.out.println(TimeStamp.getTimeStamp()+" : "+"Node: "+ Sender.getnodenumber()+" GRANT_TOKEN from node: "+process_node+" to node "+node);
			srv_socket = new Socket(Parser.hostname.get(node), port);
			PrintWriter msg_send = new PrintWriter(
					srv_socket.getOutputStream(), true);
			msg_send.println("GRANT_TOKEN from node: "+process_node);			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	public static int getnodenumber()
	{
		
		return process_node;
	}
	public static String getHostName()
	{
			
		return host;
	}	
}
