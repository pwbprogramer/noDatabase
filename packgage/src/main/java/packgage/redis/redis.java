package packgage.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class redis {
    
	@Autowired
	public RedisTemplate redisTemplate;
	
}
