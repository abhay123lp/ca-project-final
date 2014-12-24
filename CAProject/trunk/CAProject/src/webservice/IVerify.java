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
public interface IVerify {
    
    @WebMethod
    public boolean verify(String fileName, byte[] fileBytes);
}
