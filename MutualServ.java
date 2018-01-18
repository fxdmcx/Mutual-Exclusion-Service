import java.util.Random;

public class MutualServ 
{	
	private int initiator = 0;
	public int getInitiator() 
	{
		return initiator;
	}

	public void setInitiator(int initiator)
	{
		this.initiator = initiator;
	}
	private static long seed;
	private static Random random;
	private static int countRequests=0;
	static 
	{
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }
	
	public static void main(String[] args) 
	{
		
		MutualServ broad = new MutualServ();
		//Check the topology/nodes and the ports.
		Parser.checkNodes();
		
		//Process the nodes, number them
		//System.out.println("calling node process");
		SystemManager.nodeProcess();
		
		try 
		{	
			Thread.sleep(2000);
		} 
		catch (InterruptedException e)
		{		
			e.printStackTrace();
		}
		
		
		if(SystemManager.getnodenumber()==new MutualServ().getInitiator())
		{	
			System.out.println("\n\nInitiating token management...\n");
		}
		try 
		{
			
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
		
		//If I am initiator start broadcasting.
		
		//broad.doBroadcast();
		broad.manageToken();
		
	}
	
	
	void manageToken()
	{
		if(SystemManager.getnodenumber()==new MutualServ().getInitiator())
		{
			//System.out.println(TimeStamp.getTimeStamp()+" : "+"I am node "+SystemManager.getnodenumber()+" and having token");
			
			System.out.println("Total number of critical section requests per node: "+Parser.noOfRequests);
			System.out.println("Mean delay between two consecutive critical section requests: "+Parser.delayInRequests);
			System.out.println("Mean duration of critical section: "+Parser.durationInCS+"\n");
			//SystemManager.setInitiatorParent(new MutualServ().getInitiator());
			CSLogger.writeNodeEntry("I am node "+SystemManager.getnodenumber()+" and having token"+" : "+SystemManager.childQueue());
			SystemManager.setHasToken(true);
			try 
			{
				
				Thread.sleep(999);
			} 
			catch (InterruptedException e) 
			{		
				e.printStackTrace();
			}		
		}
		else
		{
			try 
			{
				
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{		
				e.printStackTrace();
			}
		}
		
		new MutualServ().startRaymond();	
	}
	
	void startRaymond()
	{
		
		int node;
		node = randomRequestNode();
	//	while((node!=SystemManager.getnodenumber()) || node== new BroadcastMain().getInitiator())
		while((node!=SystemManager.getnodenumber()))
		{
			//if(node== new BroadcastMain().getInitiator())
				//node = randomRequestNode();
			node = randomRequestNode();
		}
		//System.out.println(TimeStamp.getTimeStamp()+" : "+"I am node :"+SystemManager.getnodenumber()+" and my random number is "+node);
		CSLogger.writeNodeEntry("I am node :"+SystemManager.getnodenumber()+" and my random number is "+node+" : "+SystemManager.childQueue());
		if(countRequests<Parser.noOfRequests)
		{
			cs_enter(node);
			countRequests++;
		}
		try 
		{
			
			Thread.sleep(10);
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
				
		/*else if(Sender.getnodenumber()==4)
		{
			TokenHandler.sentRequest = true;
			SystemManager.addInChildQueue(Sender.getnodenumber());
			System.out.println("I am node "+Sender.getnodenumber()+" and sending token req. to my parent node "+TokenHandler.parent);
			Sender.requestToken(TokenHandler.parent, Parser.getPort(TokenHandler.parent));
		}*/
		
	}
	
	static int randomRequestNode()
	{
		int noOfNodes = Parser.hostname.size();
		//System.out.println(Parser.hostname.size());
		Random r = new Random();		
		int randomNode = r.nextInt(noOfNodes);
		if(randomNode <= noOfNodes){
			return randomNode;
		}
		else{
			return randomRequestNode();
		}
	}
	/**
     * Returns a real number from an exponential distribution with rate lambda.
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt>
     */
	
    public static double exp(double lambda) {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("Rate lambda must be positive");
        return -Math.log(1 - uniform()) / lambda;
    }
	
	/**
     * Return real number uniformly in [0, 1).
     */
    public static double uniform() 
	{
        return random.nextDouble();
    }
	void cs_enter(int node)
	{
		
	//	while(node== new BroadcastMain().getInitiator())
		//	node = randomRequestNode();
		//System.out.println(TimeStamp.getTimeStamp()+" : "+"I am node :"+SystemManager.getnodenumber()+" and I am in my cs_enter");
		CSLogger.writeNodeEntry("I am node :"+SystemManager.getnodenumber()+" and requesting for CS"+" : "+SystemManager.childQueue());
		if(SystemManager.getnodenumber()==node)
		{
			
			SystemManager.addInChildQueue(SystemManager.getnodenumber());
			if(!SystemManager.SentRequest())
			{
				SystemManager.setSentRequest(true);
				//System.out.println(TimeStamp.getTimeStamp()+" : "+"I am node "+SystemManager.getnodenumber()+" and sending token req. to my parent node "+TokenHandler.parent);
				CSLogger.writeNodeEntry("I am node "+SystemManager.getnodenumber()+" and sending token req. to my parent node "+SystemManager.getParent()+" : "+SystemManager.childQueue());
				SystemManager.requestToken(SystemManager.getParent(), Parser.getPort(SystemManager.getParent()));
			}
		}
	}
	
	public static void executeCS()
	{
		
		CSLogger.writeCSEntry("I am node "+Sender.getnodenumber()+" and Entering my Critical Section.");
		//CSLogger.writeCSEntry("I am node: "+Sender.getnodenumber()+" and Executing my Critical Section.");
		CSLogger.writeNodeEntry("I am node: "+Sender.getnodenumber()+" and Entering my Critical Section."+" : "+SystemManager.childQueue());
		//System.out.println(TimeStamp.getTimeStamp()+" : "+"I am node: "+Sender.getnodenumber()+" and Executing my Critical Section.");
		
		
		if(countRequests%(Parser.noOfRequests/10)==0)
		{
			System.out.println("Node :"+SystemManager.getnodenumber()+" CS executed: "+countRequests+" times");
		}
		try 
		{
			// Mean duration of critical section 10 then lamda = 1/10
			Thread.sleep(Math.round(exp(1.0/Parser.durationInCS)));
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
		cs_exit();
	}
		
	public static void cs_exit()
	{		
		CSLogger.writeCSEntry("I am node "+Sender.getnodenumber()+" and Exiting my Critical Section.\n");
		CSLogger.writeNodeEntry("I am node: "+Sender.getnodenumber()+" and Exiting my Critical Section."+" : "+SystemManager.childQueue()+"\n");		
		SystemManager.exitCriticalSection();
		try 
		{
			//Mean delay between two consecutive critical section requests then lamda = 1/50
			Thread.sleep(Math.round(exp(1/Parser.delayInRequests)));

		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
		CSLogger.writeNodeEntry(Sender.getnodenumber()+" : CS Exit done. "+" : "+SystemManager.childQueue()+"\n");
		new MutualServ().startRaymond();
			
	}
	
	
}
