package web.rainlf.mysql.mapper;

import org.apache.ibatis.annotations.*;
import web.rainlf.mysql.table.UserDO;

/**
 * Created by Administrator on 2017/7/1.
 * user table
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (id, name, username, password) " +
            "VALUES (#{id}, #{name}, #{username}, #{password})")
    void insert(UserDO userDO);

    @Delete("DELETE FROM user WHERE id=#{id}")
    void delete(@Param("id") String id);

    @Update("UPDATE user SET name=#{name}, username=#{username}, password=#{password} WHERE id=#{id}")
    void update(UserDO userDO);

    @Select("SELECT * FROM user WHERE username=#{usernmae}")
    UserDO getUserDO(String username);
}
