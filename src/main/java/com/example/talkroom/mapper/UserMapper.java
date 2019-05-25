package com.example.talkroom.mapper;

import com.example.talkroom.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Roxi酱
 */
@Mapper
@Component
public interface UserMapper {
    /**
     * insert 用来注册用户
     * @param user
     */
    @Insert("insert into user(id,name,sex) values (#{id},#{name},#{sex})")
    @Options(keyColumn = "id",useGeneratedKeys = true,keyProperty = "id")
    void insert(User user);

    /**
     *  验证登陆
     * @param user
     * @return 用户
     */
    @Select("select * from user where id=#{id} and password=#{password}")
    User select(User user);

    /**
     * 添加好友
     * @param uid1
     * @param uid2
     */
    @Insert("insert into uuconnetion(uid1,uid2) values(#{uid1},#{uid2})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void addFriend(int uid1,int uid2);

    /**
     * 通过名字得到用户
     * @param
     * @return
     */
    @Select("select id from user where name=#{name}")
    Integer getUser(String name);
}
