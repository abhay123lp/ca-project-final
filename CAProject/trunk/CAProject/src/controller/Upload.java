package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import util.PropertyLoader;
import dao.CertificateAuthority;
//import org.apache.tomcat.util.http.fileupload.FileItem;



@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public Upload() {
        super();
        
    }


    private String saveFile = PropertyLoader.loadProperty("client_upload_folder");
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			
		} else {
			FileItemFactory itemsFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(itemsFactory);
			List items = null;
			
			try {
				 items = upload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				
				if (item.isFormField()) {
					
				} else {
					String itemName = item.getName();
					if ((itemName == null) || itemName.equals("") ) {
						continue;
					}
					String fileName = FilenameUtils.getName(itemName);
					File f = checkExist(fileName);
					try {
						item.write(f);
						
						//
						// verify here
						//
						boolean result = CertificateAuthority.verify(saveFile + f.getName());
						String s = "Failed";
						if (result) s = "Certificate OK";
						request.setAttribute("result", s);
						request.getRequestDispatcher("/verify.jsp")
									.forward(request, response);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	private File checkExist(String fileName) {
		File f = new File(saveFile + fileName);
	/*	if (f.exists()) {
			StringBuffer sb = new StringBuffer(fileName);
			sb.insert(sb.lastIndexOf("."), "-" + new Date().getTime());
			f = new File(saveFile + sb.toString());
		}*/
		return f;
	}

}
