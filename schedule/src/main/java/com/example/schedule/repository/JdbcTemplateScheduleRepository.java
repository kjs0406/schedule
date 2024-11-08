package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
    // 현재 시간 구하는 코드
    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now = new Date();
    String nowTime = time.format(now);

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 일정 등록
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        // insert 쿼리를 작성하지 않아도 db에 저장이 되게 한다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);

        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("content", schedule.getContent());
        parameters.put("user_name", schedule.getUserName());
        parameters.put("user_password", schedule.getUserPassword());
        parameters.put("write_date", nowTime);
        // 최초 입력 시, 수정일은 작성일과 동일
        parameters.put("modify_date", nowTime);
        // 저장 후 생성된 key 값을 number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // ScheduleResponseDto 형태로 응답을 한다.
        return new ScheduleResponseDto(key.longValue(), schedule.getContent(), schedule.getUserName(), schedule.getUserPassword(), nowTime, nowTime);
    }

    // 전체 일정 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        // 수정일 기준 내림차순 조회
        return jdbcTemplate.query("select * from schedule order by modify_date desc", scheduleRowMapper());
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id=?", scheduleRowMapperV2(), id);
        // 조회 된 id가 없을 경우 exception을 발생시킨다.
        return result.stream().findAny().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, id + "해당 id 값이 존재하지 않습니다."));
    }

    // 선택 일정 조회
    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        // 마지막의 id 값이 ?(플레이스홀더) 와 치환되면서 값이 들어가게 된다.
        List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ?", scheduleRowMapperV2(), id);
        // null값이 있을 수도 있기 때문에 findAny()를 사용하여 List형태의 result값을 Optional형태로 변경.
        return result.stream().findAny();
    }

    // 선택 일정 수정
    @Override
    public int updateSchedule(Long id, String content, String userName, String userPassword) {

        // 아래의 쿼리가 반영된 수가 int로 반환되게 된다.
        return jdbcTemplate.update("update schedule set content = ?, user_name = ?, modify_date = ? where schedule_id = ? and user_password = ?", content, userName, nowTime, id, userPassword);
    }

    // 선택 일정 삭제
    @Override
    public int deleteSchedule(Long id) {
        // delete 지만 update를 사용하고 안에 delete 쿼리문을 작성한다.
        return jdbcTemplate.update("delete from schedule where schedule_id = ?", id);

    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {

        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                return new ScheduleResponseDto(
                        rs.getLong("schedule_id"),
                        rs.getString("content"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("write_date"),
                        rs.getString("modify_date")
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("schedule_id"),
                        rs.getString("content"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("write_date"),
                        rs.getString("modify_date")
                );
            }
        };
    }

}
