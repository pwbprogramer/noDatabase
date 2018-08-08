package packgage.welcome.webServices;

import javax.servlet.ServletContextEvent;
import javax.xml.ws.Endpoint;

import org.springframework.web.context.ContextLoaderListener;

public class webServicesListener extends ContextLoaderListener {
   
	  public void contextInitialized(ServletContextEvent event) {
	        String address="http://localhost:8083/weather";
	        Endpoint.publish(address, new weatherImpl());
	        super.contextInitialized(event);
	    }
}
