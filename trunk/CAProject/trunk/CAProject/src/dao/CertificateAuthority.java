/**
 * 
 */
package dao;

import java.io.File;

import util.Util;

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

	private static final void generatePrivateKey(DistinguishedName dn) {
		String path = Util.mkdirs(dn.getCn());
		try {
			Runtime r = Runtime.getRuntime();
			String last = "openssl genrsa -des3 -out " + path + dn.getCn()
					+ ".key -passout pass:" + dn.getPassword() + " 1024 && cd "
					+ path + " && openssl rsa -in " + dn.getCn()
					+ ".key -passin pass:" + dn.getPassword()
					+ " -outform der " + dn.getCn() + ".der";
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final void generateCSR(DistinguishedName dn) {
		String path = Util.mkdirs(dn.getCn());
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
		String path = Util.mkdirs(dn.getCn());
		try {
			Runtime r = Runtime.getRuntime();
			String last = "openssl x509 -req -days 365 -in " + path
					+ dn.getCn() + ".csr -CA " + CA_CERT + " -CAkey "
					+ CA_PRIKEY + " -passin pass:" + CA_PASSWORD
					+ " -CAcreateserial -out " + path + dn.getCn() + ".crt";
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
			return Util.zip(CLIENTS_FOLDER, dn.getCn());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final boolean verify(File clientCRT) {
		try {
			Runtime r = Runtime.getRuntime();
			String last = "openssl verify -CAfile " + CA_CERT + " " + clientCRT.getPath()
					+ " > " + VERIFY_FOLDER + clientCRT.getName();
			String[] cmd = new String[] { "/bin/bash", "-c", last };
			Process p1 = r.exec(cmd);
			p1.waitFor();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
