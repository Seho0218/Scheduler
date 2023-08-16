# Scheduler

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FSeho0218%2Fscheduler&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

# 프로젝트 개요

학생들의 수강 신청을 위한 플랫폼.

Link : http://seho0218.synology.me:3205/

로그인 링크 : http://seho0218.synology.me:3205/login

## 기본정보

- ### 기본 기능
- 기본적으로 선착순으로 처리되며 학생이 1시에 신청을 했을 경우,다른 학생은 1시를 선택할 수 없다.
- 학생이 이름을 정확히 입력하여 수강 신청하였을 경우, '수업 조회 및 변경'을 통해 수업 시간대를 변경, 조회할 수 있다.
- 이름을 정확히 입력하지 않거나 수강 신청을 하지 않은 상태에서 조회할 경우, 조회 불가 메시지를 볼 수 있다.
- 모든 입력 조회 폼에서 입력해야하는 칸에 아무것도 입력하지 않은 경우 오류 메시지가 발생한다.
- 교사 회원 가입시 승인 절차 없이 바로 스케쥴을 관리할 수 있으면 문제가 발생할 수 있기에 Spring Security와 관리자 계정을 통해 교사 아이디에 승인 절차를 부여할 수 있도록 되어있다.
- 교사와 관리자 공통적으로 수업 관리를 할 수 있고 로그인 했을 경우 다시 로그인 페이지로 갈 수 없도록 LoginController에 설정이 되어있고  로그인을 했을 때는 메인 페이지에서 로그아웃 및 수업 관리 버튼이 보여지게 되어있다.
- 로그인 했을 경우, 수업 관리 페이지에서 비밀번호를 변경할 수 있다.
- 각 기능에 대한 테스트 케이스 작성을 하였다.

- ### 인증 및 보안
- 교사 회원가입을 통해 회원가입을 할 경우, BCrypt를 통해 비밀번호가 암호화 되고 관리자의 인가를 받아서 학생의 수업을 삭제할 수 있다.
- 관리자의 권한 인가를 받은 교사만 스케쥴 수정페이지에 접근할 수 있다.
- 관리자로 로그인 했을 경우, 기본적으로 교사 권한 부여 페이지로 이동하도록 되어있으며 교사에게 권한 부여를 할 수 있다.
- 관리자 계정은 최고 권한을 갖기 때문에 회원가입을 통해 계정이 생성되지 않고 어플리케이션 실행시 하나의 계정만 생성되며 생성되어 있을 경우 추가 생성되지는 않는다.
- 누구나 관리자 아이디에 접근이 가능하도록 되어있기 때문에 주기적(1시간) 단위로 비밀번호가 README의 로그인 정보에 있는 비밀번호로 갱신되며 실제로 서비스할 경우, 이 기능은 탑재되지 않는다.
- 입력한 이메일과 아이디를 바탕으로 아이디 찾기 및 비밀번호 찾기가 가능하다.
- 아이디는 가입시 입력한 이메일로 Gmail을 통해 전송되며 비밀번호도 마찬가지로 가입시 입력한 Gmail을 통해 발송된 인증번호를 바탕으로 비밀번호를 재설정할 수 있다.
## 로그인 정보

 관리자 정보
- 아이디 : admin
- 비밀번호: root123!@#
## 사용 기술

- Spring
- SpringSecurity
- JPA
- MySQL
- Docker(Synology)
## 프로젝트 구조
```bash
프로젝트 루트/
  ├── scheduler/
  │   ├── Config/
  │   │   ├── Authority
  │   │   │     └── AdminDetails.java
  │   │   │     └── TeacherDetails.java  
  │   │   │     └── UserDetailService.java
  │   │   └── Admin.java      
  │   │   └── CustomAuthenticationFailureHandler.java
  │   │   └── SecurityConfig.java
  │   │
  │   ├── Controller/
  │   │   └── AdminController.java  
  │   │   └── BasicController.java
  │   │   └── CertController.java
  │   │   └── JoinController.java
  │   │   └── LoginController.java
  │   │   └── ManageController.java
  │   │   └── SearchClassController.java
  │   │
  │   ├── Dto/
  │   │   ├── Admin
  │   │   │     └── ApproveTeacherDTO.java
  │   │   │     └── AdminDTO.java
  │   │   ├── Teacher
  │   │   │     └── CertDTO.java
  │   │   │     └── DeleteClassDTO.java
  │   │   │     └── FindIdDTO.java
  │   │   │     └── FindPasswordDTO.java
  │   │   │     └── JoinTeacherDTO.java
  │   │   │     └── PwdEditDTO.java
  │   │   │     └── TeacherDTO.java
  │   │   ├── ClassDTO.java
  │   │   ├── ClassListDTO.java
  │   │   ├── LoginDTO.java
  │   │   └── StudentClassDTO.java
  │   │
  │   ├── Entity/
  │   │   ├── AdminEntity.java
  │   │   ├── ClassEntity.java
  │   │   └── TeacherEntity.java
  │   │
  │   ├── Mapper/
  │   │   ├── ClassMapper.java
  │   │   ├── JoinTeacherMapper.java
  │   │   ├── LoginTeacherMapper.java
  │   │   └── StudentClassMapper.java
  │   │
  │   ├── Repository/
  │   │   └── jpa
  │   │         └── AdminRepository.java
  │   │         └── ClassTableRepository.java
  │   │         └── TeacherRepository.java
  │   │
  │   └── Service/
  │       │   └──Impl
  │       │       ├── AdminServiceImpl.java
  │       │       ├── CertServiceImpl.java
  │       │       ├── JoinServiceImpl.java
  │       │       ├── CertServiceImpl.java
  │       │       ├── SearchClassServiceImpl.java
  │       │       └── SubmitServiceImpl.java
  │       │
  │       ├── AdminService.java
  │       ├── CertService.java
  │       ├── JoinService.java
  │       ├── ManageService.java
  │       ├── SearchClassService.java
  │       └── SubmitService.java
  │
  └── README.md
  ```


**scheduler**: 스케줄러 모듈의 루트 디렉토리입니다.

- **Config**: 스케줄러의 설정과 관련된 클래스들을 포함하는 디렉토리입니다.
    -  **Authority**
         -  `AdminDetails.java`: 관리자 정보를 조회하여 비교합니다.
         -  `TeacherDetails.java`: : 교사 정보를 조회하여 비교합니다.
         -  `UserDetailsService.java` : DB에서 구체적인 유저의 정보를 조회합니다.
    - **`Admin.java`**: 관리자 계정을 생성하는 클래스이고 2번 기능은 실제 운영시에는 탑재되지 않습니다. 
       - 1 :  권한은 회원 가입을 통해 접근할 수 없도록 하기 위해서 어플리케이션 실행시 자동으로 AdminEntity에 저장할 수 있게 되어있습니다.
       - 2 : 관리자 비밀번호가 공개되어 있어 타 사용자가 변경하였을 경우, 추후에 관리자 권한을 사용할 경우 문제가 발생할 수 있기에 주기적(1시간)으로 비밀번호를 변경해주는 기능있습니다.
    - `CustomAuthenticationFailureHandler.java`: 로그인 했을시 발생하는 오류를 반환하는 클래스입니다.
    - `SecurityConfig.java`: 스프링 시큐리티를 설정하고 규칙을 정의하는 클래스입니다.

- **Controller**: 스케줄러와 관련된 API 엔드포인트를 처리하는 컨트롤러 클래스를 포함하는 디렉토리입니다.
    - `BasicController.java`: 기본 API 엔드포인트를 처리하는 컨트롤러 클래스입니다.
    - `CertController.java`: 아이디찾기 및 비밀번호찾기 API 엔드포인트를 처리하는 컨트롤러 클래스입니다.
    - `JoinController.java`: 교사 회원가입 API 엔드포인트를 처리하는 컨트롤러 클래스입니다.
    - `LoginController.java`: 로그인 API 엔드포인트를 처리하는 컨트롤러 클래스입니다.
    - `ManageController.java`: 관리 API 엔드포인트를 처리하는 컨트롤러 클래스입니다.
    - `SearchClassController.java`: 스케줄러 API 엔드포인트를 처리하는 컨트롤러 클래스입니다.

- **Dto**: 데이터 전송 객체(DTO)를 포함하는 디렉토리입니다.
    - **Admin**
      - `AdminDTO.java`: 관리자 정보를 가진 DTO 클래스입니다.
      - `ApproveTeacherDTO.java`:  관리자의 교사 권한 제어를 위한  DTO 클래스입니다.
    - **Teacher**
      - `CertDTO.java`: 임시 인증번호 위한 DTO 클래스입니다.
      - `DeleteClassDTO.java`: 수업 삭제를 위한 DTO 클래스입니다.
      - `FindIdDTO.java`: 아이디를 가진 DTO 클래스입니다.
      - `FindPasswordDTO.java`: 비밀번호 찾기를 위한 DTO 클래스입니다.
      - `JoinTeacherDTO.java`: 교사 회원가입을 위한 DTO 클래스입니다.
      - `PwdEditDTO.java`: 비밀번호 변경을 위한 DTO 클래스입니다.
      - `TeacherDTO.java`: 교사 정보를 위한 DTO 클래스입니다.
    - `ClassDTO.java`: 수업 정보를 위한 DTO 클래스입니다.
    - `ClassListDTO.java`: 수업 리스트를 위한 DTO 클래스입니다.
    - `LoginDTO.java`: 로그인 데이터 전송을 위한 DTO 클래스입니다.
    - `StudentClassDTO.java`: 학생 수업 정보를 위한 DTO 클래스입니다.

  - **Entity**: 스케줄 엔티티 클래스를 포함하는 디렉토리입니다.
    - `AdminEntity.java`: 관리자 정보를 담고 있는 엔티티 클래스입니다.
    - `ClassEntity.java`: 수업 스케쥴 정보를 담고 있는 엔티티 클래스입니다.
    - `TeacherEntity.java`: 교사 정보를 담고 있는 엔티티 클래스입니다.

- **Mapper**: 엔티티와 DTO 간의 매핑을 처리하는 매퍼 클래스를 포함하는 디렉토리입니다.
    - `ClassMapper.java`: 클래스 엔티티에서 DTO로 가는 매핑을 담당하는 매퍼 클래스입니다.
    - `JoinTeacherMapper.java`: 클래스 엔티티에서 DTO로 가는 매핑을 담당하는 매퍼 클래스입니다.
    - `StudentClassMapper.java`: 학생이 신청한 수강목록 엔티티에서 DTO로 가는 매핑을 담당하는 매퍼 클래스입니다.

 - **Repository**: 스케줄 데이터에 접근하기 위한 리포지토리 인터페이스를 포함하는 디렉토리입니다.
      **jpa**
      - `AdminRepository.java`: 관리자 데이터를 관리하기 위한 리포지토리 인터페이스입니다.
      - `ClassTableRepository.java`: 수업 조회, 저장 및 관리하기 위한 리포지토리 인터페이스입니다.
      - `TeacherRepository.java`: 교사 데이터를 관리하기 위한 리포지토리 인터페이스입니다.

- **Service**: 스케줄러 비즈니스 로직을 처리하는 서비스 인터페이스와 구현체를 포함하는 디렉토리입니다.
    - `AdminService.java`: 교사 회원의 권한 승인의 허가와 비허가를 관리하는 인터페이스 입니다.
    - `CertService.java`: 아이디와 비밀번호 찾기 등의 비즈니스 로직을 담당하는 서비스 인터페이스입니다.
    - `JoinService.java`: 교사 회원가입 비즈니스 로직을 담당하는 서비스 인터페이스입니다.
    - `ManageService.java`: 수업 관리 로직을 담당하는 서비스 인터페이스입니다.
    - `SearchClassService.java`: 수업 조회 로직을 담당하는 서비스 인터페이스입니다.
    - `SubmitService.java`: 수강신청 및 수정 로직을 담당하는 서비스 인터페이스입니다.
    - Impl: 각 인터페이스를 구현한 구현체들의 폴더입니다.

- **README.md**: 프로젝트에 대한 설명과 사용 방법을 기술한 마크다운 파일입니다.


## 실제 작동 사진

 Link

## 개선여지 및 추가적으로 진행해야할 부분
 - JWT Token

## 개발 기록

https://seho0218.tistory.com/category/Develop_And_Improve/scheduler

기본적으로 해결하면서 겪은 경험들을 쓰고 날짜와 실제 해결 또는 에러를 경험한 날짜는 상이함.




