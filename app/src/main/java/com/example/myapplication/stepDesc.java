package com.example.myapplication;

public class stepDesc {

    private String stepId, stepName, stepDesc;

    public stepDesc(String stepId, String stepName, String stepDesc){

        this.stepId = stepId;
        this.stepName = stepName;
        this.stepDesc = stepDesc;

    }

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
}
