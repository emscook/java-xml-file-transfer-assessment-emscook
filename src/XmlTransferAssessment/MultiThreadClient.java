package XmlTransferAssessment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadClient implements Runnable {
	private static String USERFILEDIR = "./userFiles/";
	private int myPort;
	private String myUsername = "";
	private String myHostname = "";
	private String myDirectory = "";

	public MultiThreadClient(String myUsername, String myHostname, int myPort, String myDirectory) {
		this.myPort = myPort;
		this.myHostname = myHostname;
		this.myUsername = myUsername;
		this.myDirectory = myDirectory;
	}
	@Override
	public void run() {
		if(myDirectory.equals("")) {
			myDirectory = USERFILEDIR;
		}
		else {
			myDirectory = "./" + myDirectory;
		}
		List<String> results = new ArrayList<String>();
		File[] files = new File(myDirectory).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				results.add(file.getName());
			}
		}
		Thread[] clientThreads  = new Thread[results.size()];
		for (int i = 0; i < results.size(); i++) {
			clientThreads[i] = new Thread(new ClientWorker(this.myUsername, this.myHostname, this.myPort, results.get(i),myDirectory));
			clientThreads[i].start();
		}
		for(Thread areDone : clientThreads) {
			try {
				areDone.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Client Finished.");
	}
	public static void main(String[] args) {
		if(args.length == 0) {
			Thread clientThread = new Thread(new MultiThreadClient("emscook","127.0.0.1",10025,USERFILEDIR));
			clientThread.start();			
		}
		else if (args.length == 4) {
			Thread clientThread = new Thread(new MultiThreadClient(args[0],args[1],Integer.parseInt(args[2]),USERFILEDIR));
			clientThread.start();
		}
		else {
			System.out.println("Error starting MultiThreadClient! Requires args = String username, String server address, int port, String user file directory");
		}
	}
}
