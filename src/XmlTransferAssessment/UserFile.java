package XmlTransferAssessment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserFile {
	@XmlAttribute(name = "Username")
	private String userName;
	@XmlAttribute(name = "Date")
	private String currentDate;
	private String fileName;
	private byte[] fileContents;
	public UserFile() {
		
	}
	public UserFile(String userName, String currentDate, byte[] fileContents, String fileName) {
		this.userName = userName;
		this.currentDate = currentDate;
		this.fileContents = fileContents;
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCurrentDate() {
		return currentDate;
	}
	
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	
	public byte[] getFileContents() {
		return fileContents;
	}
	
	public void setFileContents(byte[] fileContents) {
		this.fileContents = fileContents;
	}
	
}
