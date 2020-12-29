package com.example.myapplication;

public class PickCropData {

    private int cropId;
    private String general_info;
    private String cropName;
    private String cropImage;

    public PickCropData(String cropName, String cropImage, int cropId, String general_info) {
        this.cropName = cropName;
        this.cropImage = cropImage;
        this.cropId = cropId;
        this.general_info = general_info;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getGeneral_info() {
        return general_info;
    }

    public void setGeneral_info(String general_info) {
        this.general_info = general_info;
    }
}
