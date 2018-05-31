package XmlTransferAssessment;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class ExampleRunner {
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException {
		String myAddy = InetAddress.getLocalHost().getHostAddress();
		int numFileTransfers = 1;
		String testDirectory = "userFiles/";
		String[] userNames = new String[] {"emscook"};
		Thread[] clientThreads  = new Thread[numFileTransfers];
		MultiThreadClient[] myClients = new MultiThreadClient[numFileTransfers];
		for(int i = 0; i < numFileTransfers; i++) {
			myClients[i] = new MultiThreadClient(userNames[i], myAddy, 10025,testDirectory);
			clientThreads[i] = new Thread( myClients[i]);
		}
		MultiThreadServer myServer = new MultiThreadServer(new String[]{"10025"});
		Thread serverThread = new Thread(myServer);
		serverThread.start();
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < numFileTransfers; i++) 
			clientThreads[i].start();
		for(int i = 0; i < numFileTransfers; i++) {
			clientThreads[i].join();
		}
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Got all responses in " + estimatedTime + " milliseconds.");
	}
	
	
}
