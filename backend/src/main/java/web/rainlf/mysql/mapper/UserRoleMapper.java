package web.rainlf.mysql.mapper;

import org.apache.ibatis.annotations.*;
import web.rainlf.mysql.table.UserRoleDO;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 * userRole table
 */
@Mapper
public interface UserRoleMapper {

    @Insert("INSERT INTO userRole (userId, roleId) VALUES (#{userId}, #{roleId})")
    void insert(UserRoleDO userRoleDO);

    @Delete("DELETE FROM userRole WHERE userId=#{userId}")
    void deleteAll(@Param("userId") String userId);

    @Delete("DELETE FROM userRole WHERE userId=#{userId} AND roleId=#{roleId}")
    void delete(@Param("userId") String userId, @Param("roleId") String roleId);

    @Select("SELECT roleId FROM userRole WHERE userId=#{userId}")
    List<String> getRoleIds(@Param("userId") String userId);
}
