package web.rainlf.service;

import org.springframework.http.ResponseEntity;
import web.rainlf.mysql.table.UserDO;

/**
 * Created by Administrator on 2017/7/1.
 */

public interface UserService {

    int add(UserDO userDO);
    int delete(String userId);
    int update(UserDO userDO);
    UserDO getUserDO(String username);
}
