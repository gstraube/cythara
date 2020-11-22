[![Build Status](https://travis-ci.org/gstraube/cythara.svg?branch=master)](https://travis-ci.org/gstraube/cythara)

# Cythara
A musical instrument tuner for Android.

[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
      alt="Get it on F-Droid"
      height="80">](https://f-droid.org/packages/com.github.cythara/)

NOTE: Do not use the outdated Play Store version.
The keystore file that was used to sign it has been lost, so updates can no longer be pushed to Google Play.

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

Current library version: commit [d958352](https://github.com/JorenSix/TarsosDSP/tree/d9583528b9573a97c220d19e6d9ab2929e9bd1c5)

## License

Cythara is licensed under GPLv3. A copy of the license is included in the [LICENSE](https://github.com/gstraube/cythara/blob/master/LICENSE).

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
* [SiIky](https://github.com/SiIky) added the Drop C bass tuning and suggested a more reliable way of handling note frequencies
* [romgarb](https://github.com/romgarb) added the Turkish Oud standard tuning
* [klausweiss](https://github.com/klausweiss) added the Banjo tuning and missing translations
* [berkaygunduzz](https://github.com/berkaygunduzz) provided the translation to Turkish

Thank you all!

## Screenshots

![Listening to input](/fastlane/metadata/android/en-US/phoneScreenshots/listening.png?raw=true)

![Providing feedback](/fastlane/metadata/android/en-US/phoneScreenshots/feedback.png?raw=true)

![Listing tunings](/fastlane/metadata/android/en-US/phoneScreenshots/tunings.png?raw=true)

![Listing frequencies](/fastlane/metadata/android/en-US/phoneScreenshots/choose_frequency.png?raw=true)

![Listing notations](/fastlane/metadata/android/en-US/phoneScreenshots/choose_notation.png?raw=true)
