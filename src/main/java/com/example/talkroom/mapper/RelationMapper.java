package com.example.talkroom.mapper;

import com.example.talkroom.bean.Relation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Roxi酱
 */
@Mapper
public interface RelationMapper {
    /**
     * 判断双方是否为好友
     * @param uid1
     * @param uid2
     * @return
     */
    @Select("select * from uuconnetion where uid1=#{uid1} and where uid2=#{uid2}")
    Relation judge(int uid1,int uid2);


}
