# راهنمای نصب Manus Pro AI

<div dir="rtl">

## روش‌های نصب

### روش 1: دانلود کد منبع و Build

از آنجایی که این پروژه نیاز به Android SDK دارد، باید خودتان APK را بسازید:

#### پیش‌نیازها:
- Android Studio نصب شده
- JDK 17 یا بالاتر
- Android SDK API 34

#### مراحل:

1. **کلون کردن پروژه:**
```bash
git clone https://github.com/roshandell/ManusProAI.git
cd ManusProAI
```

2. **باز کردن در Android Studio:**
   - Android Studio را باز کنید
   - File > Open را انتخاب کنید
   - پوشه ManusProAI را انتخاب کنید
   - منتظر بمانید تا Gradle sync شود

3. **Build کردن APK:**
   - Build > Build Bundle(s) / APK(s) > Build APK(s)
   - یا در Terminal اجرا کنید:
   ```bash
   ./gradlew assembleRelease
   ```

4. **پیدا کردن APK:**
   - فایل APK در مسیر زیر ساخته می‌شود:
   ```
   app/build/outputs/apk/release/app-release-unsigned.apk
   ```

5. **انتقال به گوشی:**
   - فایل APK را به گوشی اندروید خود منتقل کنید
   - روی فایل کلیک کنید و نصب کنید
   - اگر نیاز بود، "نصب از منابع ناشناخته" را فعال کنید

### روش 2: استفاده از Gradle مستقیم

اگر Android SDK دارید:

```bash
git clone https://github.com/roshandell/ManusProAI.git
cd ManusProAI
./gradlew assembleRelease
```

APK در `app/build/outputs/apk/release/` ساخته می‌شود.

## تنظیمات اولیه

بعد از نصب:

1. برنامه را باز کنید
2. به تنظیمات بروید (آیکون تنظیمات در پایین)
3. کلید API OpenAI خود را وارد کنید
4. دکمه ذخیره را بزنید

### دریافت کلید API:

1. به https://platform.openai.com بروید
2. ثبت‌نام کنید یا وارد شوید
3. به بخش API Keys بروید
4. یک کلید جدید بسازید
5. کلید را کپی کنید و در برنامه وارد کنید

## استفاده

### چت با AI:
- پیام خود را در کادر پایین بنویسید
- دکمه ارسال را بزنید
- منتظر پاسخ AI بمانید

### تولید تصویر:
- به تب "تولید تصویر" بروید
- توضیحات تصویر مورد نظر را بنویسید
- دکمه تولید را بزنید
- منتظر بمانید تا تصویر ساخته شود

### مشاهده آمار:
- به تب "داشبورد" بروید
- آمار استفاده خود را ببینید

## مشکلات رایج

### خطای API:
- مطمئن شوید کلید API صحیح است
- بررسی کنید اعتبار حساب OpenAI شما کافی است

### خطای اتصال:
- اتصال اینترنت خود را بررسی کنید
- VPN را امتحان کنید

### برنامه باز نمی‌شود:
- مطمئن شوید اندروید 7.0 یا بالاتر دارید
- برنامه را حذف و دوباره نصب کنید

## پشتیبانی

برای گزارش مشکلات یا سوالات:
https://github.com/roshandell/ManusProAI/issues

</div>
