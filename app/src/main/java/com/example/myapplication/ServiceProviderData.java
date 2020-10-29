package com.example.myapplication;

public class ServiceProviderData {

    private String serviceProviderName;
    private String serviceProviderLocation;
    private String serviceProviderTags;
    private int serviceProviderImage;
    private Boolean selected = false;


    public ServiceProviderData(String serviceProviderName, String serviceProviderLocation, String serviceProviderTags, int serviceProviderImage) {
        this.serviceProviderName = serviceProviderName;
        this.serviceProviderLocation = serviceProviderLocation;
        this.serviceProviderTags = serviceProviderTags;
        this.serviceProviderImage = serviceProviderImage;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceProviderLocation() {
        return serviceProviderLocation;
    }

    public void setServiceProviderLocation(String serviceProviderLocation) {
        this.serviceProviderLocation = serviceProviderLocation;
    }

    public String getServiceProviderTags() {
        return serviceProviderTags;
    }

    public void setServiceProviderTags(String serviceProviderTags) {
        this.serviceProviderTags = serviceProviderTags;
    }

    public int getServiceProviderImage() {
        return serviceProviderImage;
    }

    public void setServiceProviderImage(int serviceProviderImage) {
        this.serviceProviderImage = serviceProviderImage;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean b) {

        this.selected = b;

    }
}
