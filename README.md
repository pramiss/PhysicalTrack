<div align="right">
  <img width="200" height="152" alt="광운대 졸작" src="https://github.com/user-attachments/assets/7a438ca9-304a-4c55-8b8b-99cf7e0de186" />
</div>

</br>
</br>

<div align="center">
  <!-- logo -->
  <img width="45" height="45" alt="physical-track-icon" src="https://github.com/user-attachments/assets/de98d360-20c5-4ea9-a234-ba3309a41797"  />
  <img src="https://github.com/user-attachments/assets/66606481-bb15-4fa5-8465-14bd116565c5" width="400"/>
</div>

<div align="center">
  
### Physical Track: 군 장병을 위한 자동화 운동 관리 솔루션

</div>

<div align="center">
  <img width="300" alt="image" src="https://github.com/user-attachments/assets/c689db02-cf5c-4d7a-aa68-57897a58450f" />
</div>

</br>

### 📍 프로젝트 기획
**Physical Track**은 국군 장병들을 위한 '특급전사' 달성과 같은 체력 목표를 체계적으로 달성할 수 있도록 돕는 **iOS 기반 자동화 운동 카운팅 및 관리 애플리케이션**입니다. 기존 운동 앱들이 군 환경의 특수성을 반영하지 못해 발생하는 관리의 어려움과 부상 위험을 해결하고자 기획되었습니다.

<div align="center">
  <img width="700" alt="image" src="https://github.com/user-attachments/assets/7df400e5-1f99-43c2-8974-efea45127cd0" />
</div>

</br>
</br>
</br>

### 👨‍💻 개발 인원 및 기간

개발인원: 3인 (Backend 1명, iOS 1명, WEB 1명) </br>
개발기간: 약 5개월 (2024년 9월 ~ 2025년 2월)

</br>
</br>
</br>

### 📱 주요 기능 화면

| 운동 시작 | 운동 결과 및 기록 확인 | 캘린더 및 세부 통계 | 순위 및 유저 통계 |
| :---: | :---: | :---: | :---: |
| <img width="200" alt="physical-track-icon" src="https://github.com/user-attachments/assets/e8725370-2390-4efc-95ab-b9e756389531" /> | <img width="200" alt="physical-track-icon" src="https://github.com/user-attachments/assets/d64e0293-8234-4d65-9fa2-23b430da894a" /> | <img width="200" alt="physical-track-icon" src="https://github.com/user-attachments/assets/44503c29-741f-46c3-9ca2-f46484e50b34" /> | <img width="200" alt="physical-track-icon" src="https://github.com/user-attachments/assets/5358f293-0cad-493f-b0bc-f3ba5969630c" /> |
| 원하는 운동과 운동 목표를 선택하고 운동을 시작합니다. 각 운동은 센서를 통해 자동으로 개수를 카운팅합니다. | 운동 완료 후 목표 달성 여부와 검정 등급을 확인합니다. </br> 통계 페이지로 연결됩니다. | 통계 페이지에서 일주일 간의 운동 기록을 확인합니다. 해당 날짜를 클릭해서 해당 운동의 상세 통계(운동 템포)를 확인합니다. | 순위 페이지에서 종목 별 한달 간의 유저간 순위를 보여줍니다. 유저의 이름을 클릭하면 일주일 간 상세통계 를 확인 할 수 있습니다. |

</br>
</br>
</br>

### 🛠️ 기술 스택
| 개발 | Backend |
| :---: | :---: |
| Language | Java |
| Framework | Spring Boot |
| Database | MySQL |
| 배포 | AWS EC2 |
| 협업 도구 | Github / Notion / Figma |

</br>
</br>
</br>

### ✨ 핵심 개발 내용
#### 1. 데이터베이스 모델링 및 구축 (MySQL)

<img width="1200" alt="image" src="https://github.com/user-attachments/assets/b18e8c7f-78df-42c6-abb8-31b70f04d2ad" />

</br>
</br>

👉 사용자(`Users`), 운동(`Workouts`), 기록(`Records`), 피드백(`Feedbacks`) 등 서비스의 주요 데이터를 효율적으로 관리하기 위한 테이블 구조와 관계로 설계되었습니다.

👉 운동 종목마다 기록해야 할 데이터(예: 횟수, 시간, 거리)가 다른 점을 고려하여 **Records 테이블의 `workout_detail` 칼럼을 JSON 타입으로 설계**되었습니다. 향후 새로운 운동 종목이 추가되더라도 데이터베이스 스키마 변경 없이, **서버 레벨에서 대응할 수 있는 유연성**을 제공합니다.

</br>

#### 2. RESTful API 설계 및 서버 핵심 로직 개발

<img width="950" height="579" alt="api-diagram" src="https://github.com/user-attachments/assets/f6fb1594-a2db-4a17-8715-1eb35e65fb92" />

👉 **RESTful API의 도메인 구분**을 통해 프론트엔드의 API 요청을 처리합니다.

👉 계층화된 아키텍처를 통해 서비스 별로 **단일 책임(기능)을 수행**합니다.

</br>

#### 3. JWT(JSON Web Token) 기반의 인증 시스템 설계 및 구현

</br>

<img width="1091" height="422" alt="LoginAPI drawio" src="https://github.com/user-attachments/assets/30bfc306-aca6-41e2-9107-01f3e604ed5e" />

</br>
</br>

👉 **Stateless 인증 방식**을 서버의 세션 저장 부담을 줄이고, **수평적 확장(Scale-Out)**에 용이한 확장성 높은 구조를 제공합니다.

👉 토큰 기반 인증으로 다양한 클라이언트(iOS/Web)에 **일관된 인증/인가 방식을 제공하여 높은 호환성**을 유지합니다.




