package com.example.myapplication;

public class PickCropData {

    private String cropName;
    private int cropImage;
    public PickCropData(String cropName, int cropImage) {
        this.cropName = cropName;
        this.cropImage = cropImage;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getCropImage() {
        return cropImage;
    }

    public void setCropImage(int cropImage) {
        this.cropImage = cropImage;
    }
}
