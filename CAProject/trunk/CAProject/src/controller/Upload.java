package controller;

import java.io.File;
import java.io.IOException;
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

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {

			FileItemFactory itemsFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(itemsFactory);
			List<FileItem> items = null;

			try {
				items = upload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (!item.isFormField()) {
					String itemName = item.getName();
					if ((itemName == null) || itemName.equals("")) {
						continue;
					}
					String fileName = FilenameUtils.getName(itemName);
					File f = new File(saveFile + fileName);
					try {
						item.write(f);
						boolean result = CertificateAuthority.verify(saveFile + f.getName());
						String s = "Failed";
						if (result)
							s = "Certificate OK";
						request.setAttribute("result", s);
						request.getRequestDispatcher("/verify.jsp").forward(request, response);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
