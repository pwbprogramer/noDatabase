package com.berheley.ichart.security;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import com.berheley.ichart.domain.SysUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * jwt 工具类
 * @author pwb
 *
 */
@Component
public class JwtUtils {
    
	public static final String CLAIM_KEY_USERID = "userId";
	
	@Value("${jwt.tokenHead}")
	private String auth_token_start;
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	/**
	 * 获取登录用户名
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
		String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
	}
	/**
	 * 获取Claims
	 * @param token
	 * @return
	 */
	private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)//验证签名
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
	
	/**
	 * 生成过期时间
	 * @param currentTime
	 * @return
	 */
	private Date generateExpirationTime(Long currentTime) {
		return new Date(currentTime+expiration);
	}
	
	/**
	 * 生成token
	 * @param name
	 * @param map 
	 * @return
	 */
	public String generate(String name, Map<String, Object> map) {
		Long now = System.currentTimeMillis();
		map.put("sub", name);
		Date date = new Date(now);
        String token = Jwts.builder()
                .setClaims(map)
                .setIssuedAt(date)//签发时间
                .setExpiration(generateExpirationTime(now))//过期时间
                .signWith(SignatureAlgorithm.HS512, secret) //采用HS512
                .compact();
        return token;
	}
	
	/**
	 * 验证token
	 */
	public boolean validateToken(String token,SysUser user) {
		Claims claims = getClaimsFromToken(token);
		if(claims==null) {
			return false;
		}else {
			Date expirationTime = claims.getExpiration();
			if(expirationTime.after(new Date())) {
				String word = (String) claims.get("MD5pw");
				String ip = (String) claims.get("IP");
				Md5PasswordEncoder pw = new Md5PasswordEncoder();
				if(pw.isPasswordValid(word, user.getUsername()+ip, auth_token_start)) {
					return true;
				};
				return false;
			}
			return false;
		}
	}
}
