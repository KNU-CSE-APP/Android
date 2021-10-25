# KnuCse
Knu Seat Reservation Application

## Contributors
> [**나지혜**](https://github.com/njh0317)
> 
> [**천지완**](https://github.com/cheonjiwan)

## Introduction
경북대학교 컴퓨터학부 강의실 예약을 돕기위한 강의실 좌석 예약 어플리케이션입니다.

### 제공 기능

1. 회원 가입 & 로그인
    - 학교 이메일 인증
   
2. 강의실 예약
    - 시간제(계속 사용할려면 연장, 6시간)
    - 강의실 별 자리 지정(중도 예약 처럼)
    - 예약 기록

3. 자유 게시판
   - 카테고리 별 게시물(자유게시판 Q&A)
   - 게시물에 댓글 및 답글 기능

4. 공지사항
    - 학생회 공지사항
    - 학부 공지사항

## Development Environment
- Android Studio 4.1

## Application
### Application Version
- minSdkVersion : 23
- targetSdkVersion : 30

### Design Pattern
+ MVVM 패턴

### Used Tool
+ RecyclerView
  + addOnScrollListener 무한 스크롤 구현
+ Retrofit
+ Data Binding


## Database table information

서버 레포 참조

## screenshot
**SignIn, SignUp, FindPW**

<img src="https://user-images.githubusercontent.com/33089715/137843311-4c0644b0-34cf-45ba-b3a7-e90aac441560.jpg" width="180px" title="signin" alt="signin"></img>
<img src="https://user-images.githubusercontent.com/33089715/137843312-f30d6cb5-f93c-46b3-b8b8-4e923370f5ce.jpg" width="180px" title="signup" alt="signup"></img>
<img src="https://user-images.githubusercontent.com/33089715/137843300-94cfa08d-c9cf-44bc-be48-afb08aed8fa0.jpg" width="180px" title="findpw" alt="findpw"></img>

<br>

**Main Function(Reservation, Board, MyPage)**

**- Reservation**

<img src="https://user-images.githubusercontent.com/33089715/137843309-ede5891e-9a00-4b2d-aef2-e6fafc3ac69e.jpg" width="180px" title="예약 가능한 강의실 목록" alt="reservation"></img>
<img src="https://user-images.githubusercontent.com/33089715/137843307-e17b6197-fe61-426e-a623-e5e4de6e83cd.jpg" width="180px" title="예약 가능한 좌석 목록" alt="reservation_seat"></img>
<img src="https://user-images.githubusercontent.com/33089715/137843302-8790575c-449c-43b9-b0e1-805fce463b3e.jpg" width="180px" title="예약 시도 시 뜨는 팝업" alt="makereservaton"></img>
<img src="https://user-images.githubusercontent.com/33089715/137843289-5fe12a67-5657-4890-9bcf-ec51e6df23ff.jpg" width="180px" title="예약 확인 화면" alt="CompleteReservation"></img>
<img src="https://user-images.githubusercontent.com/33089715/137843294-8bf21489-e92b-4dc4-b33d-ca4ec7d2abd4.jpg" width="180px" title="예약이 완료된 후 좌석 목록 화면" alt="CompleteReservation2"></img>

**- Board**

<img src="https://user-images.githubusercontent.com/33089715/137843281-86fbbbf6-e5a5-42e2-928c-af4d2c437833.jpg" width="180px" title="게시글 목록 화면" alt="board"></img>
<img src="https://user-images.githubusercontent.com/33089715/137843314-56637851-6a6e-426c-9d60-f8b1edb97220.jpg" width="180px" title="게시글 내용과 댓글 화면" alt="writing"></img>
<img src="https://user-images.githubusercontent.com/33089715/137844526-bcfec693-8385-4a18-8ca7-9ff1e7378beb.jpg" width="180px" title="대댓글 달기 화면" alt="reply"></img>


**- MyPage**

<img src="https://user-images.githubusercontent.com/33089715/137843305-a9a7a12e-b4b4-493b-a28a-0d817167ad0d.jpg" width="180px" title="mypage" alt="mypage"></img>
<img src="https://user-images.githubusercontent.com/33089715/137843306-879217f3-213d-40f1-9eaf-7f39157554ed.jpg" width="180px" title="mypage2" alt="mypage2"></img>


## With

**Server** : [Server Repo](https://github.com/KNU-CSE-APP/Server)

**iOS** : [iOS Repo](https://github.com/KNU-CSE-APP/iOS) 
