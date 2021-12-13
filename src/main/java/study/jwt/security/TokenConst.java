package study.jwt.security;

import java.util.Date;

public abstract class TokenConst {
    public static final String SECRET="secret";
    public static final Date NOW=new Date();
    public static final Date EXPIRATION=new Date(NOW.getTime()+604800000);
}
