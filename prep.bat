if exist prep (rmdir /s /q prep)
if exist prep.7z del /q prep.7z
mkdir prep
::copy .git
xcopy /s /e /i /h /k /r .git prep\.git 
::copy app dir
xcopy /s /e /i /h /k /r app\libs prep\app\libs
xcopy /s /e /i /h /k /r app\src prep\app\src
xcopy /y /h /k /r app\.gitignore prep\app\.gitignore*
xcopy /y /h /k /r app\build.gradle prep\app\build.gradle*
if exist app\proguard-rules.pro xcopy /y /h /k /r app\proguard-rules.pro prep\app\proguard-rules.pro*
::copy gradle dir
xcopy /s /e /i /h /k /r gradle prep\gradle
::copy other files
xcopy /h /k /r .gitignore prep\.gitignore*
xcopy /h /k /r build.gradle prep\build.gradle*
xcopy /h /k /r gradle.properties prep\gradle.properties*
xcopy /h /k /r gradlew prep\gradlew*
xcopy /h /k /r gradlew.bat prep\gradlew.bat*
xcopy /h /k /r prep.bat prep\prep.bat*
xcopy /h /k /r settings.gradle prep\settings.gradle*
::archive
"C:\Program Files\7-Zip\7z.exe" a prep.7z prep
::move to destination
adb shell rm -r /sdcard/Download/android/prep.7z
adb push prep.7z /sdcard/Download/android/.
::clean up
rmdir /s /q prep
del /q prep.7z
