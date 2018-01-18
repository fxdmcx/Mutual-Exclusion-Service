import java.net.*;
import java.io.*;

public class Receiver extends Thread
{
	private static final boolean bool = true;
	private static int listen_port;
	private static Receiver sync = new Receiver();
	// protected ExecutorService threadPool = Executors.newCachedThreadPool();

	public static void doWait() {
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
	public static int getListen_port() {
		return listen_port;
	}

	public static void setListen_port(int listen_port) {
		Receiver.listen_port = listen_port;
	}
	@Override
	public void run() 
	{
		// Create a listener port and receive incoming
		// application messages from other clients.
				
		try (ServerSocket srvsocket = new ServerSocket(listen_port);) 
		{
			
			Receiver.doNotify();
			while (bool) 
			{
				try 
				{
					
					Socket recv_socket = srvsocket.accept();					
					BufferedReader reader = new BufferedReader(new InputStreamReader(recv_socket.getInputStream()));	
					String message = reader.readLine();

					if(message.contains("REQ_TOKEN"))
					{
						CSLogger.writeNodeEntry("Node: "+ Sender.getnodenumber()+" Received message "+message+" : "+TokenHandler.childQueue);
						//System.out.println(TimeStamp.getTimeStamp()+" : "+"Node: "+ Sender.getnodenumber()+" Received message "+message);
						String[] part = message.split(":");
						int senderNode = Integer.parseInt(part[1].replaceAll("\\s",""));				
						//TokenHandler.childQueue.add(senderNode);
						SystemManager.addInChildQueue(senderNode);
						if(!SystemManager.HasToken())
						{
							if(!SystemManager.SentRequest())
							{
								//TokenHandler.sentRequest = true;
								SystemManager.setSentRequest(true);
								CSLogger.writeNodeEntry("Node: "+ Sender.getnodenumber()+", I don't have token, requesting my parent"+" : "+SystemManager.childQueue());
								//System.out.println(TimeStamp.getTimeStamp()+" : "+"Node: "+ Sender.getnodenumber()+", I don't have token, requesting my parent");
								Sender.requestToken(SystemManager.getParent(), Parser.getPort(SystemManager.getParent()));
							}
						}
						else
						{
							
							//if(!TokenHandler.holderWhileInCS)
							if(!SystemManager.isHolderWhileInCS())
							{
								CSLogger.writeNodeEntry("Node: "+ Sender.getnodenumber()+", I have token and granting it to node "+TokenHandler.childQueue.get(0)+" : "+TokenHandler.childQueue);
								//System.out.println(TimeStamp.getTimeStamp()+" : "+"Node: "+ Sender.getnodenumber()+", I have token and granting it to node "+TokenHandler.childQueue.get(0));
								//TokenHandler.hasToken = false;
								SystemManager.setHasToken(false);
								Sender.grantToken(TokenHandler.childQueue.get(0), Parser.getPort(TokenHandler.childQueue.get(0)));
								//TokenHandler.parent= TokenHandler.childQueue.get(0);
								SystemManager.setParent(TokenHandler.childQueue.get(0));
								TokenHandler.childQueue.remove(0);
								
							}
							
						}
					}
					
					else if(message.contains("GRANT_TOKEN"))
					{
						CSLogger.writeNodeEntry("Node: "+ Sender.getnodenumber()+" Received message "+message+" : "+SystemManager.childQueue());
						//System.out.println(TimeStamp.getTimeStamp()+" : "+"Node: "+ Sender.getnodenumber()+" Received message "+message);
						//TokenHandler.hasToken = true;
						SystemManager.setHasToken(true);
						//TokenHandler.sentRequest = false;
						SystemManager.setSentRequest(false);
						if(!TokenHandler.childQueueIsEmpty())
						{
							if(!(SystemManager.childQueue().get(0)==Sender.getnodenumber()))
							{
								//TokenHandler.hasToken = false;
								SystemManager.setHasToken(false);
								Sender.grantToken(TokenHandler.childQueue.get(0), Parser.getPort(TokenHandler.childQueue.get(0)));							
								//TokenHandler.parent= TokenHandler.childQueue.get(0);
								SystemManager.setParent(SystemManager.childQueue().get(0));
								TokenHandler.childQueue.remove(0);
								if(!TokenHandler.childQueueIsEmpty())
								{
									//TokenHandler.sentRequest = true;
									SystemManager.setSentRequest(true);
									CSLogger.writeNodeEntry("Node: "+ Sender.getnodenumber()+", Requesting my parent again for other request in my queue"+" : "+TokenHandler.childQueue);
									//System.out.println(TimeStamp.getTimeStamp()+" : "+"Node: "+ Sender.getnodenumber()+", Requesting my parent again for other request in my queue");
									Sender.requestToken(SystemManager.getParent(), Parser.getPort(SystemManager.getParent()));
								}
							}
							
							else //if((TokenHandler.childQueue.get(0)==Sender.getnodenumber()))
							{	
								//Got the token and able to enter the Critical Section.								
								MutualServ broad = new MutualServ();
								TokenHandler.childQueue.remove(0);
								/*if(!TokenHandler.childQueueIsEmpty())
								{
									TokenHandler.parent = TokenHandler.childQueue.get(0);
								}
								else
								{
									TokenHandler.parent = Sender.getnodenumber();
								}*/
								SystemManager.setParent(Sender.getnodenumber());
								//TokenHandler.parent = Sender.getnodenumber();
								//TokenHandler.holderWhileInCS = true;
								SystemManager.setHolderWhileInCS(true);
								broad.executeCS();		// CS entering
							}
								
						}
						else
						{
							CSLogger.writeNodeEntry("Node: "+ Sender.getnodenumber()+" : My queue is empty"+" : "+TokenHandler.childQueue);
							//System.out.println(TimeStamp.getTimeStamp()+" : "+"Node: "+ Sender.getnodenumber()+" : My queue is empty");
						}
							
					}
					
					reader.close();				
				} 
				
				catch (IOException e) 
				{
					System.out.println("Exception while performing accept");
					System.out.println(e.getMessage());
				}	
			}
			
		} catch (IOException e) {
			System.out.println("Exception while performing port ops");
			System.out.println(e.getMessage());
		}
	}
	
}
