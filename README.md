<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/f167dcb7-e629-40cc-b205-cc11b547013a"/>

# 아이힐미(iHealMe)

소아과 폐과 등의 이슈로 아이가 아플 때 쉽게 병원을 찾기 힘든 부모를 위해<br>
소아청소년과 병원의 실시간 온라인 접수 서비스를 제공하는 플랫폼

# 💡 Topic
* JWT 방식을 통한 회원(유저, 병원) 관리
* 병원 페이지를 별도 구현하여 유저의 전반적인 접수 관리 기능 구현
* 유저 페이지를 별도 구현하여 진료 예약 및 병원 정보 확인 가능 구현
* 진료가 완료될 시, 유저는 후기 작성하여 병원에 대한 평가를 할 수 있고<br>
   무조건 진료를 완료해야 후기 작성을 할 수 있으므로 병원에 대한 신뢰도를 알 수 있도록 기능 구현

# 📝 Summary
의원급 1차 병원의 경우 자체적인 웹 서비스를 구축할 수 없으므로<br>
이를 위해 맞춤 예약 서비스 제공하고자 하였습니다.

그리고 유저는 병원 후기 정보 및 실시간 대기자 수를 미리 알도록 하여<br>
병원(의료 서비스) 접근성 향상을 위해 신경썼습니다.

# ⭐️ Key Function
* 로그인/회원가입
  * 사용자가 로그인을 하면 서버에서 발행해주는 토큰을 가지고 브라우저의 저장소에<br>
       토큰을 유지시키는 JWT 방식을 통한 회원 관리
  * 회원가입 페이지에서 병원 측 사용자(이하 '병원')과 일반 사용자(이하 '유저')로 구분하여<br>
       회원가입 제공
     
* 병원측 페이지
   * 병원 페이지를 별도 구현하여 유저의 전반적인 접수 관리 기능 구현
        * 병원 정보 등록, 수정 기능
        *  유저 접수 정보 조회, 접수 수락 및 거절 기능
  
* 유저측 페이지
    * 유저 페이지를 별도 구현하여 진료 예약 및 병원 정보 확인 기능 구현
         * 병원 정보 조회 기능 (실시간 대기자 수 조회 추가)
         * 병원 접수 기능
         * 병원 후기 조회, 작성, 수정 기능
         * 유저 정보 수정 기능

*  유저 페이지를 기본 / 공통 페이지로 구현하고 병원 기능을 별도의 버튼(탭)으로<br>
    구분하여 병원 아이디로 로그인 했을 때만 해당 페이지로 접속이 가능하게 구현 예정


# 🛠 Tech Stack
![아이힐미 사용기술](https://github.com/dobinpark/iHealMe/assets/53501690/e7d2a530-5b55-49bf-baab-1db3410be1e9)

# ⚙️ Architecture
MVC

# 🧑🏻‍💻 Team
Back-End 개발자 6명<br>
조장 : 방기웅<br>
조원 : 강선용, 박도빈, 박혜빈, 최지원, 홍해랑<br>

# 🤚🏻 Part
로그인/회원가입 파트 : 방기웅<br>
병원 정보 검색 및 예약 접수 파트 : 박도빈<br>
병원/유저 접수 관리 파트 : 강선용, 최지원<br>
후기 작성 및 회원정보 수정 파트 : 박혜빈, 홍해랑<br>

# 🤔 Learned
* 멀티캠퍼스 취업캠프(JAVA) 최종 프로젝트 우수상(2등)
* Spring Boot를 활용한 CRUD 기능 API 제작이 능숙해짐
* JPA를 활용하여 관계형 데이터 베이스와 프로젝트 객체를 매핑하는 법을 학습하여 적용
* AWS EC2 환경에서 프로젝트 배포 방법 학습하여 적용

# 📷 Screenshot
**메인화면**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/8bcdcad0-7268-4218-8b2e-e2227f943eb8"/>
**회원가입**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/2e812032-7abb-47a9-917c-36f185322ea4"/>
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/7dd010c0-b7bf-46ef-93e0-310e4e4cc1c0"/>
**병원 리스트**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/5d89f110-62c7-455e-b9cb-babb27c2a7f8"/>
**1. 접수가 들어왔을 때(병원)**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/46638280-2c77-4be7-8ee4-5805973f0854"/>
**1. 접수를 했을 때(유저)**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/e6c0b3b2-7f2f-4a31-af1f-82c262cc6cd2"/>
**2. 접수 버튼을 눌렀을 때(병원)**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/ec8dfcf0-4194-4027-b291-b08f1c7ab004"/>
**2. 병원에서 접수 버튼을 눌렀을 때(유저)**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/36bd7751-fef5-4769-807c-cb1ddc905422"/>
**3. 진료 버튼을 눌렀을 때(병원)**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/dee44d5d-0541-40bf-896f-b7e52d12fc7e"/>
**2. 진료 완료가 되었을 때(유저)**
<img width="100%" src="https://github.com/dobinpark/iHealMe/assets/53501690/ded92844-0aa9-4ffd-87d2-bcaf7f89f36a"/>
