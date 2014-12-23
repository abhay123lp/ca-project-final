/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @date_created Dec 22, 2014
 * @email annvcit@gmail.com
 */
public final class Util {
	static {
		CLIENTS_FOLDER = PropertyLoader.loadProperty("client_folder");
		ZIP_FOLDER = PropertyLoader.loadProperty("zip_folder");
	}
	
	private static String CLIENTS_FOLDER = null;
	private static String ZIP_FOLDER = null;;
	
	// execute một command 
	public static final void exec(String command) {
		try {
			Runtime r = Runtime.getRuntime();
			String[] cmd = new String[] { "/bin/bash", "-c", command };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// xóa thư mục
	public static final String mkdirs(String dirName) {
		String path = CLIENTS_FOLDER + dirName + "/";
		String command =  "mkdir -p " + path;
		exec(command);
		return CLIENTS_FOLDER + dirName + "/";
	}

	// xóa file csr - certificate request signing
	private static final void deleteCSRFile(String csrPath) {
		String command = "rm " + csrPath;
		exec(command);
	}

	// nén folder thành file zip để gửi cho client
	// folderPath & commonName: param cần thiết để xóa file *.csr 
		// vì chỉ gửi cho client file *.crt và file *.key (private key)
	public static final String zip(String folderPath, String commonName) {
		String csrPath = folderPath + commonName + "/" + commonName + ".csr";
		deleteCSRFile(csrPath);
		String zipFilePath = ZIP_FOLDER + commonName + ".zip";
		String cd = "cd " + folderPath + " ";
		String zip = "&& zip -r " + zipFilePath + " " + commonName;
		String command = cd + zip;
		exec(command);
		return zipFilePath;

	}

	
	/*public static final void deleteAllZip() {
		String last = "rm " + ZIP_FOLDER + "*";
		exec(last);
	}*/

	public static final String readTxtFile(String filePath) {
		return readTxtFile(new File(filePath));
	}


	private static final String readTxtFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String out = "";
			String line = "";
			while ((line = reader.readLine()) != null) {
				out += line;
			}
			reader.close();
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
