package web.rainlf.mysql.table;

/**
 * Created by Administrator on 2017/7/1.
 * userRole table
 */
public class UserRoleDO {

    private String userId;
    private String roleId;

    public UserRoleDO() {
    }

    public UserRoleDO(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
