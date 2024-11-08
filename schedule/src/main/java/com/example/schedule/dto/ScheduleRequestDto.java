package com.example.schedule.dto;

import lombok.Getter;


@Getter
public class ScheduleRequestDto {
    private String content;
    private String userName;
    private String userPassword;
    private String writeDate;
    private String modifyDate;
}

