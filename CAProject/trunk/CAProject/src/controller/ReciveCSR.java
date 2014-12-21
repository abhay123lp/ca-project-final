package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		CertificateAuthority.genCert(dn);
	}

}
