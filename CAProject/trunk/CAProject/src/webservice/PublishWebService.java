package webservice;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.xml.ws.Endpoint;

/**
 * Servlet implementation class PublishWebService
 */
@WebServlet("/PublishWebService")
public class PublishWebService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishWebService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	    String bindingURI = "http://192.168.137.231:9898/verify";
        IVerify service = new ImplVerify();
        Endpoint.publish(bindingURI, service);
        System.out.println("Server started at: " + bindingURI);
	}

}
