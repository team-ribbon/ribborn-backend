# 🎯프로젝트 RIBBORN 소개
<br><br>

![ribbornmain](https://user-images.githubusercontent.com/59018674/182017856-1b7f4792-a10d-472c-a32f-c83b952eff33.png)


   
### 당신의 옷장의 옷을 구해주세요! 
<br><br>


   
## ❓ 기획 의도       
<br>

#### 수십톤씩 무차별적으로 버려지는 의류들로 인한 환경파괴 방지와 패션에 개성을 담고싶어하는 사람들을 위한 리폼 커뮤니티 사이트입니다.

  리폼 서비스를 필요로 하는 고객과 디자이너/전문가가 채팅으로 손쉽게 만나고
  리폼에 대한 지식을 공유할 수 있는 커뮤니티 서비스입니다.


* 🤟[RIBBORN 서비스 바로가기](https://ribborn.kr/)
* 👀[서비스 시연 및 발표 영상 보러가기](유튜브 링크)

<br>

## 👥 팀 소개

Backend

<br>

팀원|github
---|---|
박성규 | https://github.com/ParkSungGyu1
이정우 | https://github.com/lky8967
박성렬 | https://github.com/songryel-park
<br>

## 🗓 프로젝트 기간

<br>

* 2022년 6월 27일 ~ 2022년 8월 5일 
  
<br>  

## 🛠️ 아키텍처

<br>
<details>
    <summary>아키텍처 바로보기</summary>

<!-- summary 아래 한칸 공백 두고 내용 삽입 -->
아키텍처 이미지 삽입



</details>


## ⛏️ ER 다이어그램

<br>

<details>
    <summary>ERD 바로보기</summary>

<!-- summary 아래 한칸 공백 두고 내용 삽입 -->
ER다이어그램 삽입



</details>

## 🔥이슈 및 트러블슈팅

<br>

<details>
<summary><b>N + 1 문제</b></summary>
  
> **문제** : User, Post, Contents, Comment 엔티티는 N:1 맵핑이 되어있기 때문에 호출 시 N + 1 문제를 야기할 수 있음
>
> **해결** : 데이터를 Flat하게 조회해야 할 경우에는 Repository에서 DTO를 바로 생성하여 리턴했으나, 해당 방법은 페이징 처리가 되지 않기 때문에 페이징이 필요한 Comment는 API를 분리해서 따로 페이징 처리를 했음
  
</details>
<details>
<summary><b>게시글 내용이 길 경우 게시글이 작성되지 않는 문제</b></summary>
  
> **문제** : 게시글 내용이 길 경우 게시글이 작성되지 않고 rollback 처리 되는 문제 발생
>
> **해결** : 프론트단에서 글자 수를 제한하는 방법으로 생각했으나, 커뮤니티 서비스 특성상 글자수를 제한하는 것은 유저 입장에서 큰 불편함을 겪을 수 있기 때문에 nginx.conf 파일에서 client_max_body_size를 조정해서 처리했음
  
</details>

<details>
<summary><b>게시글에 이모지가 작성될 경우 에러가 발생하는 문제</b></summary>
  
> **문제** : 게시글,댓글,아이디 등 String이 들어가는 모든 문구에서 이모지가 들어갈 경우 HttpMessageNotWritableException 에러가 발생했음
>
> **해결** : 이모지는 2byte보다 크기가 크기 때문에 문제가 발생할 수 있다. XSS 필터를 추가해 해결할 수 있을 것 같다(22.08.01 현재 해결 x)
  
</details>

<br>

## :hammer_and_wrench: 기능설명

<br>
<details>
<summary>1. 4종류의 게시판 CRUD</summary>
  
<div markdown="1">       

- 리폼 견적요청, 리폼 후기, 리폼 질문, 디자이너/전문가 룩북 4종류의 게시판 CRUD
- 이미지 업로드, 댓글CRUD, 게시글 좋아요 및 공유 가능

여기에 게시판 이미지

</div>
</details>

<details>
<summary>2. 채팅 (Stomp, WebSocket)</summary>
  
<div markdown="1">       

- 전문가/디자이너, 일반유저간 1 : 1 채팅방 생성
- 채팅 내용이 분 단위로 구분됨
- 좌측 채팅목록에서 기존 채팅중이던 목록을 확인할 수 있음
- 채팅 중 견적 게시글에 대해 진행중, 완료 등으로 상태 변경 가능

여기에 채팅 이미지

</div>
</details>

<details>
<summary>3. 알림 (Stomp, WebSocket)</summary>
  
<div markdown="1">       

- 해당 채팅방에 있지 않거나 오프라인 상태 시, 알림 송신
  * 새로운 메시지 전달 시
  * 해당 견적 게시글의 상태가 변경될 시
  * 전문가/디자이너가 거래 취소 시
  
여기에 알림 이미지

</div>
</details>

<details>
<summary>4. 게시글의 인기순, 최신순, 지역, 진행정도로 정렬 가능</summary>
  
<div markdown="1">       
  
여기에 정렬 이미지

</div>
</details>

<details>
<summary>5. 마이페이지 내 정보 조회 기능</summary>
  
- 본인이 작성한 게시글 / 북마크한 게시글을 확인 가능  
  
<div markdown="1">       
  
여기에 마이페이지 사진

</div>
</details>

<details>
<summary>6. 마이페이지에서 내 정보 변경 기능</summary>
  
- 닉네임 및 개인정보 변경 가능
  
<div markdown="1">       
  
정보 변경 사진
  
</div>
</details>

<details>
<summary>7. 글 작성자의 게시글 확인 기능</summary>
  
- 작성자의 마이페이지에서 게시글 확인 가능 
  
<div markdown="1">       
  
유저상세페이지 사진
  
</div>
</details>

<br>

##  API 설계

<br>

여기에 API 이미지
<br>
[API 설계 자세히보기](여기에 api 노션 링크)




