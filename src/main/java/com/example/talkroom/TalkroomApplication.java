package com.example.talkroom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author Roxi酱
 */
@MapperScan("com.example.talkroom.mapper")
@ServletComponentScan
@SpringBootApplication
public class TalkroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalkroomApplication.class, args);
    }

}
