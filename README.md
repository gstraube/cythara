[![Build Status](https://travis-ci.org/gstraube/cythara.svg?branch=master)](https://travis-ci.org/gstraube/cythara)

# Cythara
A musical instrument tuner for Android

[<img src="https://f-droid.org/badge/get-it-on.png"
      alt="Get it on F-Droid"
      height="80">](https://f-droid.org/app/com.github.cythara)

## Functionality

* Provides tunings for various instruments and supports chromatic tuning.
* Changes background color from red to green to indicate that the pitch is in tune (with a tolerance of 10 cents).
* Displays deviations between -60 and 60 cents.

## Tests

Run `./gradlew test` to run all unit tests. In addition, there are UI tests based on image comparisons which
can be run using `./gradlew connectedCheck`. The reference images are generated using a Nexus 5X emulator
(resolution: 1080 x 1920, 420 dpi) with API level 26.

## Libraries

The Tarsos DSP library (https://github.com/JorenSix/TarsosDSP) is used for pitch detection.

## License

Cythara is licensed under the GPL, version 3. A copy of the license is included in LICENSE.txt.

## Screenshots

![Listening to input](/fastlane/metadata/android/en-US/phoneScreenshots/listening.png?raw=true)

![Providing feedback](/fastlane/metadata/android/en-US/phoneScreenshots/feedback.png?raw=true)

![Listing tunings](/fastlane/metadata/android/en-US/phoneScreenshots/tunings.png?raw=true)
