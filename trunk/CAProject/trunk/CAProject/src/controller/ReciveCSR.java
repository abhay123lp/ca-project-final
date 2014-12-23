
package controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.PropertyLoader;
import dao.CertificateAuthority;
import dao.DistinguishedName;

/**
 * Servlet implementation class ReciveCSR
 */
@WebServlet(description = "Tiếp nhận thông tin Distinguished Name từ client", urlPatterns = { "/ReciveCSR" })
public class ReciveCSR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReciveCSR() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String country = request.getParameter("c").trim();
		String state = request.getParameter("st").trim();
		String local = request.getParameter("l").trim();
		String organization = request.getParameter("o").trim();
		String commonName = request.getParameter("cn").trim();
		String orgUnit = request.getParameter("ou");
		String email = request.getParameter("emailAddress").trim();
		String pwd = request.getParameter("password").trim();
		DistinguishedName dn = new DistinguishedName();
		dn.setC(country);
		dn.setSt(state);
		dn.setL(local);
		dn.setOrgName(organization);
		dn.setCn(commonName);
		dn.setEmailAddress(email);
		dn.setPassword(pwd);
		dn.setOn(orgUnit);
		String zip = CertificateAuthority.genCert(dn);
		/*
		 * response.setContentType("application/octet-stream");
		 * response.setHeader("Content-Disposition",
		 * "attachment;filename=certificate_bundle.zip"); ServletContext ctx =
		 * getServletContext(); InputStream is = (InputStream)
		 * ctx.getResourceAsStream(zip);
		 * 
		 * int read=0; byte[] bytes = new byte[BYTES_DOWNLOAD]; OutputStream os
		 * = response.getOutputStream();
		 * 
		 * while((read = is.read(bytes))!= -1){ os.write(bytes, 0, read); }
		 * os.flush(); os.close();
		 */

		// tell browser program going to return an application file
		// instead of html page

		String fileName = dn.getCn() + ".zip";

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);

		try {
			ServletOutputStream out = response.getOutputStream();

			String path = PropertyLoader.loadProperty("root");

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(zip.replace("~", path)));

			byte[] outputByte = new byte[4096];
			// copy binary context to output stream
			while (bis.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			bis.close();
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		//Util.deleteAllZip();
	}
}
