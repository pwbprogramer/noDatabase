package packgage.welcome.webServices;

import javax.jws.WebService;

@WebService
public class weatherImpl implements weather {

	public String getWeather() {
		return "今天天气良好";
	}

}
