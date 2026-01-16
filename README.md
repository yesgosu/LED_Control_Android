# LED Remote Control - Android App

Firebase를 이용한 IoT LED 원격 제어 Android 애플리케이션

![Android](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat&logo=Firebase&logoColor=black)

## 📌 프로젝트 개요

이 프로젝트는 Android 스마트폰에서 ESP8266에 연결된 LED를 Firebase 실시간 데이터베이스를 통해 원격으로 제어하는 IoT 애플리케이션입니다.

### 주요 특징
- 📱 **Native Android App**: Java 기반 Android 네이티브 앱
- 🔥 **Firebase 실시간 연동**: 즉각적인 상태 동기화
- 🎨 **직관적인 UI**: 버튼과 이미지로 상태 표시
- 🔄 **양방향 통신**: 앱 ↔ Firebase ↔ ESP8266

## 🎯 시스템 구성

이 앱은 **LED_Control_Arduino** 프로젝트와 함께 동작합니다:

```
[Android App] ←→ [Firebase Database] ←→ [ESP8266 + LED]
```

### 관련 프로젝트
- **[LED_Control_Arduino](https://github.com/yesgosu/LED_Control_Arduino)** - ESP8266 펌웨어 코드
- **웹 버전**: HTML/JavaScript 웹 인터페이스

## 🛠️ 기술 스택

### Android
- **Language**: Java
- **Min SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 30+ (Android 11+)
- **Build Tool**: Gradle
- **IDE**: Android Studio

### Backend
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase (선택사항)

### Hardware
- **MCU**: ESP8266 (NodeMCU)
- **Output**: LED

## 📂 프로젝트 구조

```
LED_Control_Android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/fbled/
│   │   │   │   └── MainActivity.java        # 메인 액티비티
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml    # UI 레이아웃
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── on.png               # LED ON 이미지
│   │   │   │   │   └── off.png              # LED OFF 이미지
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── strings.xml
│   │   │   │   └── mipmap/                  # 앱 아이콘
│   │   │   └── AndroidManifest.xml          # 앱 설정
│   │   └── androidTest/                     # 테스트 코드
│   └── build.gradle                          # 앱 빌드 설정
├── gradle/                                   # Gradle 설정
├── build.gradle                              # 프로젝트 빌드 설정
└── settings.gradle                           # 프로젝트 설정
```

## 🎨 UI 구성

### 메인 화면
```
┌──────────────────────────────────┐
│  LED Remote Control              │
│                                  │
│  ┌────────────────────────────┐ │
│  │ LED STATUS : OFF           │ │
│  │ (배경색: 노랑/초록)         │ │
│  └────────────────────────────┘ │
│                                  │
│  ┌──────────┐  ┌──────────┐    │
│  │ LED ON   │  │ LED OFF  │    │
│  └──────────┘  └──────────┘    │
│                                  │
│  ┌────────────────────────────┐ │
│  │    [LED 상태 이미지]        │ │
│  │    (on.png / off.png)      │ │
│  └────────────────────────────┘ │
│                                  │
└──────────────────────────────────┘
```

### UI 요소
| 요소 | ID | 기능 |
|------|-----|------|
| TextView | `textView` | LED 상태 표시 |
| Button (ON) | `btn01` | LED 켜기 |
| Button (OFF) | `btn02` | LED 끄기 |
| ImageView | `oneimg` | 상태 이미지 표시 |

## ⚙️ 설정 및 설치

### 1. 사전 요구사항
- Android Studio (최신 버전 권장)
- JDK 8 이상
- Android 기기 또는 에뮬레이터
- Firebase 프로젝트
- ESP8266 하드웨어 설정 완료

### 2. Firebase 프로젝트 설정

#### Firebase Console에서 Android 앱 추가
1. [Firebase Console](https://console.firebase.google.com/) 접속
2. 프로젝트 선택 또는 생성
3. "Android 앱 추가" 클릭
4. 패키지 이름 입력: `com.example.fbled`
5. `google-services.json` 다운로드

#### google-services.json 배치
```
app/
└── google-services.json  ← 여기에 배치
```

#### Firebase SDK 추가
`app/build.gradle`에 추가:
```gradle
dependencies {
    implementation platform('com.google.firebase:firebase-bom:32.0.0')
    implementation 'com.google.firebase:firebase-database'
    implementation 'com.google.firebase:firebase-analytics'
}
```

프로젝트 수준 `build.gradle`에 추가:
```gradle
buildscript {
    dependencies {
        classpath 'com.google.gms:google-services:4.3.15'
    }
}
```

앱 수준 `build.gradle` 하단에 추가:
```gradle
apply plugin: 'com.google.gms.google-services'
```

#### Firebase 보안 규칙 설정
```json
{
  "rules": {
    "LED_STATUS": {
      ".read": true,
      ".write": true
    }
  }
}
```

⚠️ **프로덕션에서는 더 엄격한 보안 규칙 사용 필요**

### 3. 프로젝트 클론 및 빌드

```bash
# 저장소 클론
git clone https://github.com/yesgosu/LED_Control_Android.git
cd LED_Control_Android

# Android Studio에서 프로젝트 열기
# File → Open → LED_Control_Android 폴더 선택

# gradle-wrapper.properties 확인
# distributionUrl=https\://services.gradle.org/distributions/gradle-7.4-bin.zip

# 빌드 및 실행
./gradlew build
./gradlew installDebug
```

### 4. 리소스 파일 추가

#### 이미지 파일 준비
`app/src/main/res/drawable/` 폴더에 추가:
- `on.png` - LED 켜진 상태 이미지
- `off.png` - LED 꺼진 상태 이미지

#### activity_main.xml (예시)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp">

    <TextView
        android:id="@+id/textView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="LED STATUS : OFF"
        android:textSize="24sp"
        android:textAlignment="center"
        android:padding="20dp"
        android:background="@android:color/holo_green_light" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center"
        android:layout_marginTop="20dp">

        <Button
            android:id="@+id/btn01"
            android:layout_width="150dp"
            android:layout_height="80dp"
            android:text="LED ON"
            android:textSize="20sp"
            android:layout_margin="10dp" />

        <Button
            android:id="@+id/btn02"
            android:layout_width="150dp"
            android:layout_height="80dp"
            android:text="LED OFF"
            android:textSize="20sp"
            android:layout_margin="10dp" />
    </LinearLayout>

    <ImageView
        android:id="@+id/oneimg"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_marginTop="30dp"
        android:src="@drawable/off" />

</LinearLayout>
```

### 5. AndroidManifest.xml 설정

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.fbled">

    <!-- 인터넷 권한 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="LED Control"
        android:theme="@style/Theme.AppCompat.Light.DarkActionBar">
        
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

## 🚀 사용 방법

### 1. ESP8266 설정
먼저 [LED_Control_Arduino](https://github.com/yesgosu/LED_Control_Arduino) 프로젝트로 ESP8266에 펌웨어를 업로드하세요.

### 2. 앱 실행
1. Android 기기에 앱 설치
2. 앱 실행
3. 인터넷 연결 확인

### 3. LED 제어
- **LED 켜기**: "LED ON" 버튼 터치
  - 배경색이 노란색으로 변경
  - LED 켜진 이미지 표시
  - 실제 LED가 켜짐
  
- **LED 끄기**: "LED OFF" 버튼 터치
  - 배경색이 초록색으로 변경
  - LED 꺼진 이미지 표시
  - 실제 LED가 꺼짐

### 4. 상태 확인
- 상단 TextView에 현재 LED 상태 표시
- 다른 기기(웹/앱)에서 변경해도 자동 동기화

## 🔄 동작 원리

### Firebase 실시간 동기화
```java
// Firebase 참조 생성
DatabaseReference myRef = database.getReference("LED_STATUS");

// 실시간 리스너 등록
myRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String ledState = dataSnapshot.getValue(String.class);
        textView.setText("LED STATUS : " + ledState);
        // UI 자동 업데이트
    }
});
```

### 데이터 쓰기
```java
// ON 버튼 클릭
onButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        myRef.setValue("ON");  // Firebase에 "ON" 저장
        textView.setBackgroundColor(Color.YELLOW);
        oneImage.setImageResource(R.drawable.on);
    }
});
```

## 📊 데이터베이스 구조

### Firebase Realtime Database
```json
{
  "LED_STATUS": "OFF"
}
```

| 경로 | 타입 | 값 | 설명 |
|------|------|-----|------|
| LED_STATUS | String | "ON" / "OFF" | LED 상태 |

## 🎨 UI 상태 변화

### LED ON 상태
- **TextView**: 
  - 텍스트: "LED STATUS : ON"
  - 배경색: YELLOW (#FFFF00)
- **ImageView**: `on.png` 표시

### LED OFF 상태
- **TextView**: 
  - 텍스트: "LED STATUS : OFF"
  - 배경색: GREEN (#00FF00)
- **ImageView**: `off.png` 표시

## 🐛 문제 해결

### Firebase 연결 실패
```
증상: 앱이 실행되지만 버튼 클릭이 작동하지 않음
해결:
1. google-services.json 파일이 app/ 폴더에 있는지 확인
2. Firebase Console에서 패키지 이름 확인 (com.example.fbled)
3. 인터넷 권한 확인 (AndroidManifest.xml)
4. Firebase 보안 규칙 확인
```

### 빌드 오류
```
증상: Gradle sync failed
해결:
1. Android Studio 업데이트
2. Gradle 버전 확인
3. Firebase SDK 버전 확인
4. File → Invalidate Caches / Restart
```

### 앱이 크래시됨
```
증상: 앱 실행 시 즉시 종료
해결:
1. Logcat 확인 (View → Tool Windows → Logcat)
2. on.png, off.png 파일 존재 확인
3. Firebase 초기화 확인
4. 권한 설정 확인
```

### 실제 LED가 동작하지 않음
```
증상: 앱은 정상이지만 LED 반응 없음
해결:
1. ESP8266 시리얼 모니터 확인
2. ESP8266의 WiFi 연결 확인
3. Firebase URL이 동일한지 확인
4. LED 하드웨어 연결 확인
```

## 📈 개선 및 확장 아이디어

### 기능 추가
- [ ] 로그인 기능 (Firebase Authentication)
- [ ] 사용자별 LED 관리
- [ ] LED 밝기 조절 (SeekBar)
- [ ] RGB LED 색상 선택 (ColorPicker)
- [ ] 타이머 기능
- [ ] 푸시 알림
- [ ] 사용 이력 로그
- [ ] 여러 LED 동시 제어
- [ ] 위젯 지원

### UI/UX 개선
- [ ] Material Design 적용
- [ ] 다크 모드 지원
- [ ] 애니메이션 효과
- [ ] 소리/진동 피드백
- [ ] 다국어 지원

### 기술적 개선
- [ ] MVVM 아키텍처 적용
- [ ] Kotlin으로 마이그레이션
- [ ] Jetpack Compose UI
- [ ] Room Database (로컬 캐싱)
- [ ] WorkManager (백그라운드 작업)

## 🔒 보안 고려사항

### Firebase 보안 규칙 강화
```json
{
  "rules": {
    "LED_STATUS": {
      ".read": "auth != null",
      ".write": "auth != null"
    }
  }
}
```

### 권장 사항
1. **Firebase Authentication** 사용
2. **ProGuard** 설정으로 코드 난독화
3. **API 키 보호** (gradle.properties 사용)
4. **HTTPS** 통신 (Firebase 기본 제공)

## 📱 테스트

### 단위 테스트
```bash
./gradlew test
```

### UI 테스트
```bash
./gradlew connectedAndroidTest
```

### 수동 테스트 체크리스트
- [ ] 앱 설치 및 실행
- [ ] LED ON 버튼 클릭
- [ ] LED OFF 버튼 클릭
- [ ] 실시간 상태 업데이트 확인
- [ ] ESP8266 LED 동작 확인
- [ ] 웹에서 변경 시 앱 동기화 확인
- [ ] 네트워크 끊김 시 동작 확인

## 📚 참고 자료

### 공식 문서
- [Android Developers](https://developer.android.com/)
- [Firebase Android SDK](https://firebase.google.com/docs/android/setup)
- [Firebase Realtime Database](https://firebase.google.com/docs/database/android/start)

### 튜토리얼
- [Firebase Android Codelab](https://firebase.google.com/codelabs/firebase-android)
- [Android Studio Guide](https://developer.android.com/studio/intro)

### 관련 프로젝트
- [LED_Control_Arduino](https://github.com/yesgosu/LED_Control_Arduino)

## 🤝 기여하기

버그 리포트, 기능 제안, 코드 개선은 언제나 환영합니다!

1. Fork this repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📝 라이선스

MIT License

## 👨‍💻 개발자

**yesgosu** - [GitHub](https://github.com/yesgosu)


**마지막 업데이트**: 2025년 1월

⭐ 이 프로젝트가 도움이 되셨다면 Star를 눌러주세요!
