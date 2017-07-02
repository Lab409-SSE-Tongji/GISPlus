package web.rainlf.authentication.user;

/**
 * Created by Justin on 2017/6/2.
 */


public interface UserInfoService{

    UserInfo getUserInfo(String username);
    UserInfo addUserInfo(UserInfo userInfoToAdd);
}
