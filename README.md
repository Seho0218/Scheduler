# Scheduler

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FSeho0218%2Fscheduler&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

# 프로젝트 개요

학생들의 수강 신청을 위한 플랫폼.

Link : http://seho0218.synology.me:3205/

로그인 링크 : http://seho0218.synology.me:3205/login

## 기본정보

### 기본 기능

#### 공통 기능
- 모든 입력 조회 폼에서 입력해야하는 칸에 아무것도 입력하지 않은 경우 오류 메시지가 발생한다.
- 교사와 관리자는 이메일을 통해 아이디 찾기와 비밀번호 찾기를 할 수 있으며 이메일 변경도 가능하다.
- 기본적으로 선착순으로 처리되며 학생이 1시에 신청을 했을 경우,다른 학생은 1시를 선택할 수 없다.
- 학생 명부에 등록된 학생만 수강 신청과 시간표 변경을 할 수 있다.
- Spring security 와 관리자 계정을 통해 교사 아이디에 승인 절차를 부여할 수 있도록 되어있다.
- 관리자 계정은 최고 권한을 갖기 때문에 회원가입을 통해 계정이 생성되지 않고   
  어플리케이션 실행시 하나의 계정만 생성되며 생성되어 있을 경우 추가 생성되지는 않는다.

#### 관리자
- 공지사항을 작성 및 수정 삭제할 수 있다.
- 교사의 권한과 역할을 포함한다.
- 교사 회원 가입시 승인 절차 없이 바로 스케쥴을 관리할 수 있으면 문제가 발생할 수 있기에  
  회원가입한 교사에게 회원 가입한 교사의 권한 인가와 회수를 할 수 있다.
- 학생의 담임 교사를 변경할 수 있다.  
  '갑'선생님에서 '을'선생님으로 담임교사를 변경할 때, 시간이 겹칠 경우 오류메시지를 보여준다. 
- 교사 계정을 삭제할 수 있으며 삭제할시, 시간표도 같이 삭제된다.
- 어플리케이션 실행시 자동으로 1개의 계정만 생성된다.

#### 교사
- 회원 가입한 후, 관리자의 권한 인가를 받은 교사만 역할을 수행 할 수 있다.
- 학생을 등록, 삭제하고 시간표를 변경할 수 있다.
- 학생을 삭제할 경우, 학생이 등록한 시간표가 같이 사라진다.
- 교사별 스케쥴이 따로 있어 '갑'선생님이 월요일 1시에 수업이 있어도 '을'선생님이 월요일 1시에 수업을 할 수 있다.

#### 학생
- 담당 교사가 등록해줬을 경우, 수업을 조회하거나 신청, 변경을 할 수 있다.

- ### 인증 및 보안
- 교사 회원가입을 통해 회원가입을 할 경우, BCrypt를 통해 비밀번호가 암호화 되고 관리자의 인가를 받아서 학생의 수업을 삭제할 수 있다.
- 누구나 관리자 아이디에 접근이 가능하도록 되어있기 때문에 주기적(1시간) 단위로 비밀번호가 README의 로그인 정보에 있는 비밀번호로 갱신되며 실제로 서비스할 경우, 이 기능은 탑재되지 않는다.
- 입력한 이메일과 아이디를 바탕으로 아이디 찾기 및 비밀번호 찾기가 가능하다.
- 아이디는 가입시 입력한 이메일로 gmail을 통해 전송되며 비밀번호도 마찬가지로 가입시 입력한 gmail을 통해 발송된 인증번호를 바탕으로 비밀번호를 재설정할 수 있다.

## 로그인 정보

 관리자 정보
- 아이디 : admin
- 비밀번호: root123!@#

## 사용 기술

- Spring
- JPA
- QueryDSL
- SpringSecurity
- MySQL
- Docker(Synology)

## Architecture
![drawio.png](..%2F..%2F..%2FDownloads%2Fdrawio.png)

## 프로젝트 구조
```bash
프로젝트 루트/
  ─main
        │  ├─java
        │  │  └─com
        │  │      └─attendance
        │  │          └─scheduler
        │  │              ├─admin
        │  │              │  ├─api
        │  │              │  ├─application
        │  │              │  ├─domain
        │  │              │  ├─dto
        │  │              │  └─repository
        │  │              ├─common
        │  │              │  └─dto
        │  │              ├─config
        │  │              │  └─Authority
        │  │              ├─course
        │  │              │  ├─api
        │  │              │  ├─application
        │  │              │  ├─domain
        │  │              │  ├─dto
        │  │              │  └─repository
        │  │              ├─student
        │  │              │  ├─api
        │  │              │  ├─application
        │  │              │  ├─domain
        │  │              │  ├─dto
        │  │              │  └─repository
        │  │              └─teacher
        │  │                  ├─api
        │  │                  ├─application
        │  │                  ├─domain
        │  │                  ├─dto
        │  │                  └─repository
        │  └─resources
        │      ├─static
        │      │  ├─css
        │      │  └─js
        │      └─templates
        │          ├─admin
        │          ├─cert
        │          ├─class
        │          └─manage
        └─test
            └─java
                └─com
                    └─attendance
                        └─scheduler
                            ├─admin
                            │  └─application
                            ├─config
                            ├─course
                            ├─student
                            └─teacher
  ```


**scheduler**: 스케줄러 모듈의 루트 디렉토리다.  
이전에는 계층형으로 프로젝트를 진행하였으나 파일 관리와 역할 분담에 어려운 점이 발생하여 도메인 디렉토리로 변경하였다.

- PS. DDD를 배우면서 적용시켜가는 중이라 디렉토리는 계속해서 바뀌고 있습니다.


- ui : 컨트롤러를 담당하고 있습니다.
- application: 서비스 계층을 담당하고 있습니다.
- domain : 엔티티를 담당하고 있습니다.
- Repository : 저장소를 담당하고 있습니다.


- **Config**: 스케줄러의 설정과 관련된 클래스들을 포함하는 디렉토리입니다.
  - 로그인과 보안에 기본 구조에 관련된 정보가 있습니다.

- **admin**
  - 관리자 로직을 가지고 있는 디렉토리입니다.

- **common**: 
  - 로그인과 관련 로직 그리고 인증과정에서 사용될 로직을 가진 디렉토리입니다.

- **course**: 
  - 수업과 관련된 로직이 있는 디렉토리입니다.

- **student**:
  - 수업관련 로직이 있는 디렉토리 입니다

- **teacher**: 
  - 교사와 관련된 로직이 있는 디렉토리 입니다.

- **README.md**: 프로젝트에 대한 설명과 사용 방법을 기술한 마크다운 파일입니다.


## 실제 작동 사진

 Link

## 개선여지 및 추가적으로 진행해야할 부분
 - JWT Token
 - 각 기능에 대한 테스트케이스 작성
 - 애그리거트 구조 정리

## 개발 기록

https://seho0218.tistory.com/category/Develop_And_Improve/scheduler

기본적으로 해결하면서 겪은 경험들을 쓰고 날짜와 실제 해결 또는 에러를 경험한 날짜는 상이함.




