import java.util.ArrayList;

public class TokenHandler 
{

	private static int parent;
	public static ArrayList<Integer> childQueue = new ArrayList<Integer>();
	
	private static boolean hasToken = false;
	private static boolean excutingCS = false;
	private static boolean sentRequest = false;
	
	private static boolean holderWhileInCS = false; //Holding token while exectuing CS.
	
	public static boolean childQueueIsEmpty()
	{
		if(childQueue.size()==0)
			return true;
		else
			return false;
	}
	public static int getParent() {
		return parent;
	}

	public static void setParent(int parent) {
		TokenHandler.parent = parent;
	}


	public static boolean HasToken() {
		return hasToken;
	}

	public static void setHasToken(boolean hasToken) {
		TokenHandler.hasToken = hasToken;
	}

	public static boolean isExcutingCS() {
		return excutingCS;
	}

	public static void setExcutingCS(boolean excutingCS) {
		TokenHandler.excutingCS = excutingCS;
	}

	public static boolean SentRequest() {
		return sentRequest;
	}

	public static void setSentRequest(boolean sentRequest) {
		TokenHandler.sentRequest = sentRequest;
	}

	public static boolean isHolderWhileInCS() {
		return holderWhileInCS;
	}

	public static void setHolderWhileInCS(boolean holderWhileInCS) {
		TokenHandler.holderWhileInCS = holderWhileInCS;
	}
	
}
