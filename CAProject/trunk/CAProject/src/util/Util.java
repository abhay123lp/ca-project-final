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

	private static final String CLIENTS_FOLDER = "~/Desktop/clients_folder/";
	private static final String ZIP_FOLDER = "~/Desktop/zip_folder/";
	private static final String UPLOAD_FOLDER = "~/Desktop/clientupload_folder/";

	public static final String mkdirs(String dirName) {
		String path = "";
		try {
			Runtime r = Runtime.getRuntime();
			path = "mkdir -p " + CLIENTS_FOLDER + dirName + "/";
			String[] cmd = new String[] { "/bin/bash", "-c", path };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CLIENTS_FOLDER + dirName + "/";
	}

	private static final void deleteCSRFile(String csrPath) {
		try {
			Runtime r = Runtime.getRuntime();
			String last = "rm " + csrPath;
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String zip(String folderPath, String commonName) {
		String csrPath = folderPath + commonName + "/" + commonName + ".csr";
		deleteCSRFile(csrPath);
		String zipPath = ZIP_FOLDER + commonName + ".zip";
		try {
			Runtime r = Runtime.getRuntime();
			String last = "cd " + folderPath + " && zip -r " + zipPath + " "
					+ commonName;
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zipPath;

	}

	public static final void deleteAllZip() {
		try {
			Runtime r = Runtime.getRuntime();
			String last = "rm " + ZIP_FOLDER + "*";
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static final String readTxtFile(String filePath) {
		return readTxtFile(new File(filePath));
	}

	public static final String readTxtFile(File file) {
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
