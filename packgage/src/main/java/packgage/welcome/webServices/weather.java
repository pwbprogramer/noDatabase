package packgage.welcome.webServices;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface weather {
    @WebMethod
	public String getWeather();
}
