package web.rainlf.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.rainlf.Util.UuidUtil;
import web.rainlf.mysql.mapper.RoleMapper;
import web.rainlf.mysql.mapper.UserMapper;
import web.rainlf.mysql.mapper.UserRoleMapper;
import web.rainlf.mysql.table.UserDO;
import web.rainlf.mysql.table.UserRoleDO;
import web.rainlf.service.UserService;

/**
 * Created by Administrator on 2017/7/1.
 */
@Service
@Transactional
public class UserServiceImp implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public int add(UserDO userDO) {
        String userId = UuidUtil.getUUID();
        userDO.setId(userId);
        userMapper.insert(userDO);
        String roleId = roleMapper.getRoleDO("ADMIN").getId();
        userRoleMapper.insert(new UserRoleDO(userId, roleId));
        return 0;
    }

    @Override
    public int delete(String userId) {
        userMapper.delete(userId);
        userRoleMapper.deleteAll(userId);
        return 0;
    }

    @Override
    public int update(UserDO userDO) {
        userMapper.update(userDO);
        return 0;
    }

    @Override
    public UserDO getUserDO(String username) {
        return userMapper.getUserDO(username);
    }
}
