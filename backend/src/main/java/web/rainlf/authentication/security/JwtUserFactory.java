package web.rainlf.authentication.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import web.rainlf.authentication.user.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Justin on 2017/6/2.
 */

public class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserInfo userInfo) {
        return new JwtUser(
                userInfo.getUsername(),
                userInfo.getPassword(),
                mapToGrantedAuthorities(userInfo.getRoles()),
                userInfo.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
