# F1-WebApp
WebApp which aims to make ability to make predictions of driver standings from Formula 1 session (Sprint/Qualifying/Race) and see statistics from it

## Functionalities
- JWT & Cookie authorization & authentication

After registration/signing-in:
- Posting predictions for Sprint/Qualifying/Race and seeing gained points
- Printing participant standings in specific season;
- Printing standings from each session, race weekend in the specific year;
- Printing WorldRecords - maximum, minimum points ever gained using Joker/without using Joker (points-booster), in Sprint/non-Sprint race weekend, in specific session;
- Printing Personal Best Records - minimum/maximum points ever gained in specific session;

## Stack
- Jolpica F1 - https://api.jolpi.ca/ergast/f1/ - to download driver standings from specific sessions, compare it to participant predictions and count points
- MS SQL Server 22 + Docker
- Java 23
- Java Spring
- Spring Security
- React.js with Bootstrap, ChartJS

## Screenshots
### Predicting Qualifying
- Predicting
<img width="740" height="907" alt="image" src="https://github.com/user-attachments/assets/1105533f-dc1b-4a3c-bc32-cb70c2f5c901" />

- After posting predictions
<img width="833" height="862" alt="image" src="https://github.com/user-attachments/assets/d46ad7df-8736-4693-9b1f-ccb150dc7f7f" />

- After session ended
<img width="820" height="868" alt="image" src="https://github.com/user-attachments/assets/2b1e25e2-fe2e-472a-919d-5a018d14f469" />

- Session which wasn't predicted 30 minutes before its start by participant
<img width="734" height="234" alt="image" src="https://github.com/user-attachments/assets/3929b331-e909-4dd8-b248-c2eee10d7c84" />

### Predicting Sprint
It's the same as with Qualifying

### Predicting Race
- Predicting (you have to guess driver with fastest lap additionally)
<img width="744" height="907" alt="image" src="https://github.com/user-attachments/assets/0f9a6cb9-8d21-4b10-86fc-ac2b3988317f" />

- After session ended
<img width="836" height="887" alt="image" src="https://github.com/user-attachments/assets/1ce31c04-47ec-4a53-901f-51233c364957" />

- After posting predictions and if session wasn't predicted in time, it's similar cases to Qualifying

### World Records
<img width="882" height="856" alt="image" src="https://github.com/user-attachments/assets/21613433-2233-4e12-9148-f4c1ab010610" />

### Personal Best
<img width="1091" height="467" alt="image" src="https://github.com/user-attachments/assets/1c3e9bfe-13d5-4c8c-b547-42784d86d301" />

### Participant standings
<img width="1111" height="849" alt="image" src="https://github.com/user-attachments/assets/71cbf171-d096-4c30-8434-194197c41555" />

### Season results
<img width="1086" height="866" alt="image" src="https://github.com/user-attachments/assets/3a7a815d-e969-4f9a-a6fe-0557f7198f22" />

### Participants page
<img width="1325" height="738" alt="image" src="https://github.com/user-attachments/assets/4ee4b5fc-cf7a-4bbf-820d-8af354bfb1b3" />

### Home page
<img width="1092" height="802" alt="image" src="https://github.com/user-attachments/assets/e702f963-44cd-422d-97b4-e776036db917" />

### Sign-in page
<img width="533" height="481" alt="image" src="https://github.com/user-attachments/assets/68627a80-c78d-4294-b787-17bd0642d059" />

### Registration page
<img width="523" height="741" alt="image" src="https://github.com/user-attachments/assets/5c7c90bf-9099-4ccb-9a9f-bf72a408a42e" />

