package webservice;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @email annvcit@gmail.com
 * @date_created Dec 25, 2014
 *
 */
public interface IUpload {
    public void upload(String fileName, byte[] fileBytes);
    public byte[] download(String fileName);
}
