package webservice;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.ws.WebServiceException;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @email annvcit@gmail.com
 * @date_created Dec 25, 2014
 *
 */
public class ImplUpload implements IUpload{


    @Override
    public void upload(String fileName, byte[] fileBytes) {
String filePath = "/home/annvcit/Desktop/test_webservice/server/upload/" + fileName;
        
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream outputStream = new BufferedOutputStream(fos);
            outputStream.write(fileBytes);
            outputStream.close();
            
            System.out.println("Received file: " + filePath);
            
        } catch (IOException ex) {
            System.err.println(ex);
            throw new WebServiceException(ex);
        }
    }

 
    @Override
    public byte[] download(String fileName) {
        String filePath = "/home/annvcit/Desktop/test_webservice/server/download/" + fileName;
        System.out.println("Sending file: " + filePath);
        
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream inputStream = new BufferedInputStream(fis);
            byte[] fileBytes = new byte[(int) file.length()];
            inputStream.read(fileBytes);
            inputStream.close();
            
            return fileBytes;
        } catch (IOException ex) {
            System.err.println(ex);
            throw new WebServiceException(ex);
        }       
    }

}
