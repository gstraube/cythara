[![Build Status](https://travis-ci.org/gstraube/cythara.svg?branch=master)](https://travis-ci.org/gstraube/cythara)

# Cythara
A guitar tuner for Android

[<img src="https://f-droid.org/badge/get-it-on.png"
      alt="Get it on F-Droid"
      height="80">](https://f-droid.org/app/com.github.cythara)

## Functionality

* Supports standard guitar tuning (see https://en.wikipedia.org/wiki/Guitar#Standard)
* Changes background color from red to green to indicate that the string is in tune (with a tolerance of 5 cents)
* Displays deviations between -60 and 60 cents

## Tests

Run `./gradlew test` to run all unit tests. In addition, there are UI tests based on image comparisons which
can be run using `./gradlew connectedCheck`. The reference images are generated using a Nexus 5X emulator
(resolution: 1080 x 1920, 420 dpi) with API level 26.

## Libraries

The Tarsos DSP library (https://github.com/JorenSix/TarsosDSP) is used for pitch detection.

## License

Cythara is licensed under the GPL, version 3. A copy of the license is included in LICENSE.txt.
