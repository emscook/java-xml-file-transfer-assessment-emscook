package XmlTransferAssessment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
public class ClientHandler implements Runnable{
	private Socket mySocket;
	public ClientHandler(Socket mySocket) {
		this.mySocket = mySocket;
	}
	@Override
	public void run() {
		try (
				OutputStream os = mySocket.getOutputStream();
				InputStream br = mySocket.getInputStream();)
				{
			JAXBContext context = JAXBContext.newInstance(UserFile.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			UserFile workingUser = (UserFile) unmarshaller.unmarshal(br);
			String path = "./" + workingUser.getUserName() + "/" + workingUser.getCurrentDate();
			String fileName = workingUser.getFileName();
			new File(path).mkdirs();
			try (FileOutputStream fos = new FileOutputStream(path + "/" + fileName)) {
				   fos.write(workingUser.getFileContents());
				}			
		} catch (BindException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
