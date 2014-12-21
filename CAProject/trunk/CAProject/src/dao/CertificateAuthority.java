/**
 * 
 */
package dao;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @date_created Dec 21, 2014
 * @email annvcit@gmail.com
 */
public final class CertificateAuthority {

	private static final String CA_FOLDER = "~/Desktop/ca_folder/";
	private static final String CA_PRIKEY = "~/Desktop/ca_folder/prikey.key";
	private static final String CA_CERT = "~/Desktop/ca_folder/cacert.crt";
	private static final String CLIENTS_FOLDER = "~/Desktop/clients_folder/";
	private static final String VERIFY_FOLDER = "~/Desktop/verify_folder/";
	private static final String CA_PASSWORD = "password";
	private static final String ZIP_FOLDER = "~/Desktop/zip_folder/";

	private static final void generatePrivateKey(DistinguishedName dn) {
		String path = mkdirs(dn.getCn());
		try {
			Runtime r = Runtime.getRuntime();
			String last = "openssl genrsa -des3 -out " + path + dn.getCn()
					+ ".key -passout pass:" + dn.getPassword() + " 1024";
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final void generateCSR(DistinguishedName dn) {
		String path = mkdirs(dn.getCn());
		try {
			Runtime r = Runtime.getRuntime();
			String last = "openssl req -new -key " + path + dn.getCn()
					+ ".key -passin pass:" + dn.getPassword() + " -out " + path
					+ dn.getCn() + ".csr -subj " + dn.toString();
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String genCert(DistinguishedName dn) {
		generatePrivateKey(dn);
		generateCSR(dn);
		String path = mkdirs(dn.getCn());
		try {
			Runtime r = Runtime.getRuntime();
			String last = "openssl x509 -req -days 365 -in " + path
					+ dn.getCn() + ".csr -CA " + CA_CERT + " -CAkey "
					+ CA_PRIKEY + " -passin pass:" + CA_PASSWORD
					+ " -CAcreateserial -out " + path + dn.getCn() + ".crt";
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
			return zip(CLIENTS_FOLDER, dn.getCn());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final String mkdirs(String commonName) {
		String path = "";
		try {
			Runtime r = Runtime.getRuntime();
			path = "mkdir -p " + CLIENTS_FOLDER + commonName + "/";
			String[] cmd = new String[] { "/bin/bash", "-c", path };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CLIENTS_FOLDER + commonName + "/";
	}

	public static final boolean verify(String filePath) {
		try {
			Runtime r = Runtime.getRuntime();
			String last = "openssl verify -CAfile " + CA_CERT + " " + filePath
					+ " > " + VERIFY_FOLDER + "1";
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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

}
