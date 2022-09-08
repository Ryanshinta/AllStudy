package live.allstudy.util;

import io.jsonwebtoken.JwtBuilder;

import java.util.UUID;

public class JwtUtils {
    public static final Long EXPIRE = 1000 * 60 * 60 * 24L;
    public static final String APP_SECRET = "Ryan";

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }


    //create JWT token


}
