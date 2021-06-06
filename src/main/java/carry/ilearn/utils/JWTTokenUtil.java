package carry.ilearn.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于进行Token的加密和解密。主要用于生产 token, 解析token, 校验token
 *
 * @author Carry
 * @since 2021/5/26
 */
public class JWTTokenUtil {
    // Token请求头
    public static final String TOKEN_HEADER = "Authorization";
    // Token前缀
    public static final String TOKEN_PREFIX = "Bearer ";
    
    // 过期时间
    public static final long EXPIRATION = 1000 * 60 * 60;
    public static final long EXPIRATION_REMEMBERED = EXPIRATION * 24 * 7;
    
    // 应用密钥
    public static final String APP_SECRET_KEY = "secret";
    // 角色权限声明
    private static final String ROLE_CLAIMS = "role";
    
    /**
     * 生成Token。签发JWT
     */
    public static String createToken(String username, String role, boolean rememberMe) {
        Map<String, Object> claims = new HashMap<>();
        // 主体
        claims.put(ROLE_CLAIMS, role);
        
        long expiration = rememberMe ? EXPIRATION_REMEMBERED : EXPIRATION;
        return Jwts.builder()
                   .setSubject(username)
                   .setClaims(claims)
                   .claim("username", username)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(Instant.now().toEpochMilli() + expiration))
                   .signWith(SignatureAlgorithm.HS512, APP_SECRET_KEY)
                   .compact();
    }
    
    /**
     * 校验Token。验证JWT
     */
    public static Boolean validateJWT(String token) {
        return !isTokenExpired(token);
    }
    
    /**
     * 从Token中获取username
     */
    public static String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims.get("username").toString();
    }
    
    /**
     * 从Token中获取用户角色
     */
    public static String getUserRole(String token) {
        Claims claims = parseToken(token);
        return claims.get("role").toString();
    }
    
    /**
     * 查看Token是否过期
     */
    public static boolean isTokenExpired(String token) {
        return getExpireTime(token).before(new Date());
    }
    
    /**
     * 获取token的过期时间
     */
    public static Date getExpireTime(String token) {
        return parseToken(token).getExpiration();
    }
    
    /**
     * 解析JWT
     */
    private static Claims parseToken(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody();
    }
    
}
