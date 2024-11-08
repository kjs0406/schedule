package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;


    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 등록
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto.getContent(), dto.getUserName(), dto.getUserPassword());

        return scheduleRepository.saveSchedule(schedule);
    }

    // 전체 일정 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        return scheduleRepository.findAllSchedules();
    }

    // 선택 일정 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    // 선택 일정 수정
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String content, String userName, String userPassword) {

        if (content == null || userName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 일정과 작성자가 빈값이면 안됩니다.");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, content, userName, userPassword);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + userPassword + "해당 id과 passoward를 확인해주세요");
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    // 선택 일정 삭제
    @Override
    public void deleteSchedule(Long id) {

        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + " : 해당 id 값이 존재하지 않습니다." );
        }
    }
}
