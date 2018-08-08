package packgage.welcome.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityManagerSupport implements UserDetailsService {

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
          System.out.println(userName);
          return null;
	}

}
