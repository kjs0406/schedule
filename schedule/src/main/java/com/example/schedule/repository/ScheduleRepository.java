package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Schedule findScheduleByIdOrElseThrow(long id);

    // null 값을 안전하게 다루기 위해 Optional 로 wrapping
    Optional<Schedule> findScheduleById(Long id);

    int updateSchedule(Long id, String content, String userName, String userPassword);

    int deleteSchedule(Long id);

}
