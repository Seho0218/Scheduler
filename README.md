# Scheduler

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FSeho0218%2Fscheduler&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

# 프로젝트 개요

학생들의 수강 신청을 위한 플랫폼.

Link : http://seho0218.synology.me:3205/

로그인 링크 : http://seho0218.synology.me:3205/login

## 기본정보

- ### 기본 기능
- 기본적으로 선착순으로 처리되며 학생이 1시에 신청을 했을 경우,다른 학생은 1시를 선택할 수 없게됌.
- 학생이 이름을 정확히 입력하여 수강 신청하였을 경우, '수업 조회 및 변경'을 통해 수업 시간대를 변경, 조회할 수 있다.
- 이름을 정확히 입력하지 않거나 수강신청하지 않은 상태에서 조회할 경우, 조회 불가 메시지를 볼 수 있음.
- 모든 입력 조회폼에서 입력해야하는 칸에 아무것도 입력하지 않은 경우 오류 메시지가 뜸.
- 각 기능에 대한 테스트 케이스 작성

- ### 인증 및 보안
- 교사 폼을 통해 회원가입을 할 경우, BCrypt를 통해 비밀번호가 암호화 되고 관리자의 인가를 받아서 학생의 수업을 삭제할 수 있다.
- SpringSecurity를 통해 교사 권한을 받은 사람만 수정페이지게 접근할 수 있다.
- 아이디 찾기를 할 때, 회원가입했을 때, 입력한 이메일을 통해 가입한 아이디를 전달받을 수 있다.
- 비밀번호 찾기를 할 때, 입력한 이메일을 통해 임시 인증번호를 전달받고 인증될 경우 비밀번호를 수정할 수 있다. 

## 사용 기술

- Spring
- SpringSecurity
- JPA
- MySQL

## 스크린샷

## 개발 기록

https://seho0218.tistory.com/category/Develop_And_Improve/scheduler

기본적으로 해결하면서 겪은 경험들을 쓰고 날짜와 실제 해결 또는 에러를 경험한 날짜는 상이함.




