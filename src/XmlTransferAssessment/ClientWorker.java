package XmlTransferAssessment;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ClientWorker implements Runnable {
	//private static String USERFILEDIR = "userFiles/";
	private int myPort = -1;
	private String myUsername = "";
	private String myHostname = "";
	private String myFilename = "";
	private String myDirectory = "";
	@Override
	public void run() {
		System.out.println("Strating " + this.myFilename);
		if (this.myHostname.equals("")) {
			System.out.println("Bad! No Hostname Given to constructor of ClientWorker. Assuming localhost.");
			this.myHostname = "127.0.0.1";
		}
		if (this.myUsername.equals("")) {
			System.out.println("Bad! No Username Given to constructor of ClientWorker. Assuming emscook.");
			this.myUsername = "emscook";
		}
		if (this.myPort ==-1) {
			System.out.println("Bad! No Port Given to constructor of ClientWorker. Assuming 10025.");
			this.myPort = 10025;
		}
		if (this.myDirectory.equals("")) {
			System.out.println("Bad! No Directory Given to constructor of ClientWorker. Assuming emscook.");
			this.myDirectory = "userFiles/";
		}
		
		
		String pattern = "yyyy-MM-dd";
		Date now = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String strDate = simpleDateFormat.format(now);
		Path path = Paths.get(myDirectory + myFilename);
		byte[] data = new byte[0];
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserFile myFile = new UserFile(this.myUsername, strDate, data, myFilename);

		try {
			JAXBContext context = JAXBContext.newInstance(UserFile.class);
			Marshaller marshaller = context.createMarshaller();
			try (Socket client = new Socket(this.myHostname, this.myPort);
					OutputStream os = client.getOutputStream()) {
				marshaller.marshal(myFile, os);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub		
	}
	public ClientWorker(String myUsername, String myHostname, int myPort, String myFilename, String myDirectory) {
		this.myPort = myPort;
		this.myHostname = myHostname;
		this.myUsername = myUsername;
		this.myFilename = myFilename;
		this.myDirectory = myDirectory;
	}
}
