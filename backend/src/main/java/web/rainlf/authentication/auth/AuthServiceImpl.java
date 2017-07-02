package web.rainlf.authentication.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.rainlf.authentication.security.JwtTokenUtil;
import web.rainlf.authentication.security.JwtUser;
import web.rainlf.authentication.user.UserInfo;
import web.rainlf.authentication.user.UserInfoService;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Justin on 2017/6/3.
 */

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserInfoService userInfoService;

    private String tokenHead = "Authorization";

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserInfoService userInfoService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userInfoService = userInfoService;
    }

    @Override
    public UserInfo register(UserInfo userInfoToAdd) {
        final String username = userInfoToAdd.getUsername();
        if (userInfoService.getUserInfo(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userInfoToAdd.getPassword();
        userInfoToAdd.setPassword(encoder.encode(rawPassword));
        userInfoToAdd.setLastPasswordResetDate(new Date());
        userInfoToAdd.setRoles(Arrays.asList("ROLE_USER"));
        return userInfoService.addUserInfo(userInfoToAdd);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken uptoken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(uptoken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
