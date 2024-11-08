package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String content;
    private String userName;
    private String userPassword;
    private String writeDate;
    private String modifyDate;

    public ScheduleResponseDto(Schedule schedule) {

        this.id = schedule.getId();
        this.content = schedule.getContent();
        this.userName = schedule.getUserName();
        this.userPassword = schedule.getUserPassword();
        this.writeDate = schedule.getWriteDate();
        this.modifyDate = schedule.getModifyDate();
    }
}
