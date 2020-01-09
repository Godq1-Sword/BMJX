package pri.gdq.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: Jwt工具类
 *
 * @author : gdq
 * data : 2019-11-19 11:37
 **/
@Component
public class JwtUtil {
    // 签名算法名称
    private static String algorithm;
    // 密钥
    private static String secret;
    // 过期时间
    private static long expiration;
    // 签发人
    private static String iss;

    /**
     * description : 生成jwt
     *
     * @param claims 身份信息
     * @return String
     * @author : gdq
     * data : 2019/11/19 0019 15:51
     */
    public static String generateJWT(Map<String, Object> claims) {
        // 签名算法
        SignatureAlgorithm signatureAlgorithm = null;
        try {
            signatureAlgorithm = (SignatureAlgorithm) SignatureAlgorithm.class.getField(algorithm).get(SignatureAlgorithm.class);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return Jwts.builder()
                .signWith(signatureAlgorithm, secret)
                .setIssuer(iss)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * description : 查看身份是否过期
     *
     * @param jwt token
     * @return boolean
     * @author : gdq
     * data : 2019/11/19 0019 15:53
     */
    public static boolean isExpiration(String jwt) {
        return parserJwt(jwt).getExpiration().before(new Date());
    }

    /**
     * description : 解析jwt
     *
     * @param jwt token
     * @return Claims
     * @author : gdq
     * data : 2019/11/19 0019 15:53
     */
    public static Claims parserJwt(String jwt) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody();
    }

    @Value("${config.common.algorithm}")
    public void setAlgorithm(String algorithm) {
        JwtUtil.algorithm = algorithm;
    }

    @Value("${config.common.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    @Value("${config.common.expiration}")
    public void setExpiration(long expiration) {
        JwtUtil.expiration = expiration;
    }

    @Value("${config.common.iss}")
    public void setIss(String iss) {
        JwtUtil.iss = iss;
    }
}
