[![Build Status](https://travis-ci.org/gstraube/cythara.svg?branch=master)](https://travis-ci.org/gstraube/cythara)

# Cythara
A musical instrument tuner for Android.

[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
      alt="Get it on F-Droid"
      height="80">](https://f-droid.org/packages/com.github.cythara/)
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png"
      alt="Get it on Google Play"
      height="80">](https://play.google.com/store/apps/details?id=com.github.cythara)

## Functionality

* Provides tunings for various instruments and supports chromatic tuning.
* Changes background color from red to green to indicate that the pitch is in tune (with a tolerance of 10 cents).
* Displays deviations between -60 and 60 cents.
* Supports scientific pitch notation and Solfège.

## Tests

Run `./gradlew test` to run all unit tests. In addition, there are UI tests based on image comparisons which
can be run using `./gradlew connectedCheck`. The reference images are generated using a Nexus 5X emulator
(resolution: 1080 x 1920, 420 dpi) with API level 26.

## Libraries

The Tarsos DSP library (https://github.com/JorenSix/TarsosDSP) is used for pitch detection.

Current library version: commit [c26e500](https://github.com/JorenSix/TarsosDSP/tree/c26e5004e203ee79be1ec25c2603b1f11b69d276)

## License

Cythara is licensed under GPLv3. A copy of the license is included in LICENSE.txt.

# Contributors

In chronological order:
* [mtbu](https://github.com/mtbu) added the violin tuning
* [afmachado](https://github.com/afmachado) provided the translation to Brazilian Portuguese
* [tebriz159](https://github.com/tebriz159) created the logo
* [toXel](https://github.com/toXel) provided the translation to German
* [TacoTheDank](https://github.com/TacoTheDank) enabled the installation on external storage, upgraded the language level, and updated dependencies
* [thim](https://github.com/thim) added the cello tuning, fixed issues, and updated library versions
* [obibon](https://github.com/obibon) provided the translation to Basque
* [Daveed9](https://github.com/Daveed9) added the viola tuning

Thank you all!

## Screenshots

![Listening to input](/fastlane/metadata/android/en-US/phoneScreenshots/listening.png?raw=true)

![Providing feedback](/fastlane/metadata/android/en-US/phoneScreenshots/feedback.png?raw=true)

![Listing tunings](/fastlane/metadata/android/en-US/phoneScreenshots/tunings.png?raw=true)

![Listing frequencies](/fastlane/metadata/android/en-US/phoneScreenshots/choose_frequency.png?raw=true)

![Listing notations](/fastlane/metadata/android/en-US/phoneScreenshots/choose_notation.png?raw=true)
