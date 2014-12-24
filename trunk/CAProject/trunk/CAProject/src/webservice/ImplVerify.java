package webservice;


import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @email annvcit@gmail.com
 * @date_created Dec 25, 2014
 *
 */

@WebService
public class ImplVerify implements IVerify{

    private String VERIFY_FOLDER = util.PropertyLoader.loadProperty("verify_folder");
    
    @WebMethod
    @Override
    public boolean verify(String fileName, byte[] fileBytes) {
        IUpload uploader = new ImplUpload();
        uploader.upload(fileName, fileBytes);
        String filePath = VERIFY_FOLDER + fileName;
        return dao.CertificateAuthority.verify(filePath);
    }

}
