package com.example.talkroom.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Roxi酱
 */
@Data
@NoArgsConstructor
public class User {
    int id;
    String name;
    String sex;
    String password;
    public User(String name, @NotNull String password, String sex){
        this.name=name;
        this.password=password;
        this.sex=sex;
    }
    public User(int id,String password){
        this.id=id;
        this.password=password;
    }
}
