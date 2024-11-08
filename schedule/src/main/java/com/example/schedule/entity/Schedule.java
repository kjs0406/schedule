package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String content;
    private String userName;
    private String userPassword;
    private String writeDate;
    private String modifyDate;


    public Schedule(String content, String userName, String userPassword) {
        this.content = content;
        this.userName = userName;
        this.userPassword = userPassword;
    }
    public void update(String content, String userName, String userPassword) {
        this.content = content;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}