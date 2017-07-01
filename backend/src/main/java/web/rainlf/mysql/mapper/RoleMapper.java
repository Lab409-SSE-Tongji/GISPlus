package web.rainlf.mysql.mapper;

import org.apache.ibatis.annotations.*;
import web.rainlf.mysql.table.RoleDO;

/**
 * Created by Administrator on 2017/7/1.
 * role table
 */
@Mapper
public interface RoleMapper {

    @Insert("INSERT INTO role (id, role) VALUES (#{id}, #{role})")
    void insert(RoleDO roleDO);

    @Delete("DELETE FROM role WHERE id=#{id}")
    void delete(@Param("id") String id);

    @Select("SELECT * FROM role WHERE role=#{role}")
    RoleDO getRoleDO(@Param("role") String role);
}
