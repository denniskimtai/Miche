package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class stepDesc implements Parcelable {

    private String stepId, stepName, stepDesc;

    public stepDesc(String stepId, String stepName, String stepDesc){

        this.stepId = stepId;
        this.stepName = stepName;
        this.stepDesc = stepDesc;

    }

    protected stepDesc(Parcel in) {
        stepId = in.readString();
        stepName = in.readString();
        stepDesc = in.readString();
    }

    public static final Creator<stepDesc> CREATOR = new Creator<stepDesc>() {
        @Override
        public stepDesc createFromParcel(Parcel in) {
            return new stepDesc(in);
        }

        @Override
        public stepDesc[] newArray(int size) {
            return new stepDesc[size];
        }
    };

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepDesc() {
        return stepDesc;
    }

    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(stepId);
        parcel.writeString(stepName);
        parcel.writeString(stepDesc);
    }
}
