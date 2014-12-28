/**
 * 
 */
package dao;

import java.io.File;

import util.PropertyLoader;
import util.Util;
import dao.DistinguishedName;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @date_created Dec 21, 2014
 * @email annvcit@gmail.com
 */
public final class CertificateAuthority {
	// static {
	// CA_PRIKEY = PropertyLoader.loadProperty("ca_prikey");
	// CA_CERT = PropertyLoader.loadProperty("ca_cert");
	// CLIENTS_FOLDER = PropertyLoader.loadProperty("clients_folder");
	// VERIFY_FOLDER = PropertyLoader.loadProperty("verify_folder");
	// CA_PASSWORD = PropertyLoader.loadProperty("ca_password");
	// }
	private static String CA_PRIKEY = PropertyLoader.loadProperty("ca_prikey");
	private static String CA_CERT = PropertyLoader.loadProperty("ca_cert");
	private static String CLIENTS_FOLDER = PropertyLoader.loadProperty("clients_folder");
	private static String VERIFY_FOLDER = PropertyLoader.loadProperty("verify_folder");
	private static String CA_PASSWORD = PropertyLoader.loadProperty("ca_password");

	// tạo private key cho client => tạo csr
	private static final void generatePrivateKey(DistinguishedName dn) {
		String path = Util.mkdirs(dn.getCn());
		String genPriKey = "openssl genrsa -des3 -out " + path + dn.getCn() + ".key -passout pass:" + dn.getPassword() + " 1024";
		Util.exec(genPriKey);
	}

	// tạo certificate signing request => tạo certificate
	private static final void generateCSR(DistinguishedName dn) {
		String path = Util.mkdirs(dn.getCn());
		String genCSR = "openssl req -new -key " + path + dn.getCn() + ".key -passin pass:" + dn.getPassword() + " -out " + path + dn.getCn() + ".csr -subj " + dn.toString();
		Util.exec(genCSR);
	}

	// tạo certificate cho client
	// return về đường dẫn file zip chứa file *.cert và *.key => cho client
	// download
	public static final String genCert(DistinguishedName dn) {
		generatePrivateKey(dn);
		generateCSR(dn);

		String path = Util.mkdirs(dn.getCn());
		String genCert = "openssl x509 -req -days 365 -in " + path + dn.getCn() + ".csr -CA " + CA_CERT + " -CAkey " + CA_PRIKEY + " -passin pass:" + CA_PASSWORD + " -CAcreateserial -out " + path
				+ dn.getCn() + ".crt";
		Util.exec(genCert);
		return Util.zip(CLIENTS_FOLDER, dn.getCn());
	}

	// verify certificate
	public static final boolean verify(String certPath) {
		File clientCRT = new File(certPath);
		String textFilePath = VERIFY_FOLDER + clientCRT.getName() + ".txt";
		String command = "openssl verify -CAfile " + CA_CERT + " " + clientCRT.getPath() + " > " + textFilePath;
		Util.exec(command);
		String result = Util.readTxtFile(textFilePath);
		Util.exec("rm -rf " + textFilePath);
		Util.exec("rm -rf " + certPath);
		if (result.contains("OK")) {
			return true;
		}
		return false;
	}
}
