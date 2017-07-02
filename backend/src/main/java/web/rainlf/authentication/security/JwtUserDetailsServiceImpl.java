package web.rainlf.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.rainlf.authentication.user.UserInfo;
import web.rainlf.authentication.user.UserInfoService;

/**
 * Created by Justin on 2017/6/2.
 */

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoService.getUserInfo(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException(String.format("No userInfo found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(userInfo);
        }
    }
}
