# schedule
API 명세서

![image](https://github.com/user-attachments/assets/08b2e0c6-4fc7-4520-ad65-0a8408e20c2a)


ERD

![image](https://github.com/user-attachments/assets/da5dac6e-1b33-4c75-8085-fac69d53694f)


SPl
create table schedule(
  schedule_id BIGINT NOT NULL AUTO_INCREMENT,
  content TEXT COMMENT "할일",
  write_date DATETIME,
  modify_date DATETIME,
  user_name VARCHAR(20),
  user_password VARCHAR(20),
  PRIMARY KEY (schedule_id)
);

일정관리를 하기 위한 코드입니다.
일정 등록 : 할일, 작성자, 비밀번호를 json형식으로 작성하여 POST방식으로 보내면 id 값과 작성한 시간, 수정한 시간이 자동으로 기록되어 db에 저장되게 됩니다.
전체 일정 조회 : GET 방식으로 url만 입력한다면 전체 일정이 조회가 됩니다.
선택 일정 조회 : GET 방식으로 url 뒤에 id값을 입력하면 해당 id값의 일정이 조회가 됩니다.
선택 일정 수정 : PUT 방식으로 url 뒤에 id 값을 입력, json형식으로 수정할 할일, 작성자, 비밀번호를 보내면 해당 값들로 수정되며(비밀번호는 수정되지 않으며, 비밀번호가 다를 시 exception이 발생합니다.), 수정한 시간은 현재 시간으로 수정됩니다.  
선택 일정 삭제 : DELETE 방식으로 url 뒤에 id값을 입력하면 해당 id값의 일정이 삭제가 됩니다.
