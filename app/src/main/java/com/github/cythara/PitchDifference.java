package com.github.cythara;

import android.os.Parcel;
import android.os.Parcelable;

class PitchDifference implements Parcelable {

    final Note closest;
    final double deviation;
    private final Tuning tuning = new GuitarTuning();

    PitchDifference(Note closest, double deviation) {
        this.closest = closest;
        this.deviation = deviation;
    }

    private PitchDifference(Parcel in) {
        closest = tuning.findNote(in.readString());
        deviation = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(closest.getName());
        dest.writeDouble(deviation);
    }

    public static final Parcelable.Creator<PitchDifference> CREATOR
            = new Parcelable.Creator<PitchDifference>() {
        public PitchDifference createFromParcel(Parcel in) {
            return new PitchDifference(in);
        }

        public PitchDifference[] newArray(int size) {
            return new PitchDifference[size];
        }
    };
}