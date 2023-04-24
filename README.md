# PlayerK 🎧

Audio player for Kotlin Multiplatform Mobile

Library that acts as common interface for platform specific audio players.

| 	 Android	 | 		iOS 	 |
|	   ---		 |		---		 |
| 	  Media3	 |	 AVPlayer  |

---

## Usage ⚙️


```kotlin
// Get handler object.
val handler = PlayerK.getHandler()

// Use it to request actions.
handler.handler {
	setSource(Audio.Data(audioSrc = "<your-url>"))
	play()
}

// Use it to observe player state changes.
val statusFlow : Flow<PlayerStatus> = 
	handler.observe { getStatus() }

```

---

## Prerequisite for Android

Add required entry to `Manifest.xml`

```
        <service
            android:name="io.github.bartoshr.playerK.PlayerService"
            android:exported="true"
            android:foregroundServiceType="mediaPlayback">
            <intent-filter>
                <action android:name="androidx.media3.session.MediaSessionService" />
            </intent-filter>
        </service>

```

Add in `onCreate` method of your Activity. 

```kotlin
PlayerK.connect(this)
```

---


## Modules 📦

Application is composed of 4 modules

1. **android-sample** - produce android sample app
2. **io-sample** - produce iOS sample app
3. **shared** - shared module used by both apps. Enapsulate common logic. 
4. **playerK** - library module used to crate artifacts   

---

## Run iOS sample 🍎


1. Run `./gradlew assembleXCFramework`
2. Move to `ios-sample` directory
3. Run `pod install`
3. Open `ios-sample/iosApp.xcworkspace`
4. Build project


---

## Run android sample 🤖


1. Open project in Android Studio
2. Build project


---


 
 