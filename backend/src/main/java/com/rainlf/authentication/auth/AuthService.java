package com.rainlf.authentication.auth;


import com.rainlf.authentication.user.UserInfo;

/**
 * Created by Justin on 2017/6/3.
 */

public interface AuthService {

    /**
     * register
     * @param userInfoToAdd
     * @return
     */
    UserInfo register(UserInfo userInfoToAdd);

    /**
     * login
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * refresh
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);
}
