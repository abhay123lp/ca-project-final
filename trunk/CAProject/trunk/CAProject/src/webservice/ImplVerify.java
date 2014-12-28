package webservice;


import javax.jws.WebMethod;
import javax.jws.WebService;

import util.PropertyLoader;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @email annvcit@gmail.com
 * @date_created Dec 25, 2014
 *
 */

@WebService
public class ImplVerify implements IVerify{

    private String CLIENT_UPLOAD_FOLDER = PropertyLoader.loadProperty("client_upload_folder");
    
    @WebMethod
    @Override
    public boolean verify(String fileName, byte[] fileBytes) {
        IUpload uploader = new ImplUpload();
        uploader.upload(fileName, fileBytes);
        String filePath = CLIENT_UPLOAD_FOLDER + fileName;
        System.out.println(filePath);
        return dao.CertificateAuthority.verify(filePath);
    }

}
