About Weather
=============

Simple weather application that uses OpenWeatherMaps API.


Features
========

Displays current weather information for your current location and 5-day forecast.
Supports pull-to-refresh data updates.


Installation
============

Download the Weather application from [Play Store](https://play.google.com/store/apps/details?id=com.ondrejruttkay.weather.android).


Usage
=====

Start the app and select either Today's weather or Forecast from the navigation drawer.


Changelog
=========

1.0.0.0 - Initial release


Building project
================

This chapter describes how to build APK with Gradle and prepare app for publishing.

You don't need to install Gradle on your system, because there is a [Gradle Wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html). The wrapper is a batch script on Windows, and a shell script for other operating systems. When you start a Gradle build via the wrapper, Gradle will be automatically downloaded and used to run the build.

1. Clone this repository
2. Open configuration file _/mobile/src/main/java/com/ondrejruttkay/weather/android/WeatherConfig.java_ and set constants as required (see below for more info)
3. Open main build script _/mobile/build.gradle_ and set constants and config fields as required (see below for more info)
4. Run `gradlew assemble` in console
5. APK should be available in _/mobile/build/outputs/apk_ directory

**Note:** You will also need a "local.properties" file to set the location of the SDK in the same way that the existing SDK requires, using the "sdk.dir" property. Example of "local.properties" on Windows: `sdk.dir=C:\\adt-bundle-windows\\sdk`. Alternatively, you can set an environment variable called "ANDROID\_HOME".

**Tip:** Command `gradlew assemble` builds both - debug and release APK. You can use `gradlew assembleDebug` to build debug APK. You can use `gradlew assembleRelease` to build release APK. Debug APK is signed by debug keystore. Release APK is signed by own keystore, stored in _/extras/keystore_ directory.

**Signing process:** Keystore passwords are automatically loaded from property file during building the release APK. Path to this file is defined in "keystore.properties" property in "gradle.properties" file. If this property or the file does not exist, user is asked for passwords explicitly.


WeatherConfig.java
------------------

This is the main configuration file and there are some important constants: addresses to API endpoints, API keys to 3rd party services etc. Make sure that all constants are set up properly.


build.gradle
------------

This is the main build script and there are 4 important constants for defining version code and version name.

* VERSION\_MAJOR
* VERSION\_MINOR
* VERSION\_PATCH
* VERSION\_BUILD

See [Versioning Your Applications](http://developer.android.com/tools/publishing/versioning.html#appversioning) in Android documentation for more info.

There are also a build config fields in this script. Check "buildTypes" configuration and make sure that all fields are set up properly for debug and release. It is very important to correctly set these true/false switches before building the APK.

* LOGS - true for showing logs
* DEV\_API - true for development API endpoint

**Important:** Following configuration should be used for release build type, intended for publishing on Google Play:

```groovy
buildConfigField "boolean", "LOGS", "false"
buildConfigField "boolean", "DEV_API", "false"
``` 


Dependencies
============

* [Android Support Library v4](http://developer.android.com/tools/extras/support-library.html)
* [AppCompat](https://developer.android.com/reference/android/support/v7/appcompat/package-summary.html)
* [Google Play Services](http://developer.android.com/google/play-services/index.html)
* [GSON](http://code.google.com/p/google-gson/)
* [Otto](https://github.com/square/otto)
* [Picasso](https://github.com/square/picasso)
* [Retrofit](https://github.com/square/retrofit)

Testing
=======

* Test app on different Android versions (handset, tablet)
* Test overdraws
* Test offline/empty/progress states
* Test migration from old version to the new one
* Test slow internet connection
* Test reboot (boot receivers, alarms, services)
* Test analytics tracking
* Monkey test (fast clicking, changing orientation)


Publishing
==========

* Set proper versions in the main build script
* Check build config fields in the main build script
* Update text info in changelog/about/help
* Add analytics events for new features
* Set Android key hash on developers.facebook.com


Developed by
============

[Ondrej Ruttkay](https://www.linkedin.com/in/ondrejruttkay)


License
=======

    Copyright 2015 Ondrej Ruttkay

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
