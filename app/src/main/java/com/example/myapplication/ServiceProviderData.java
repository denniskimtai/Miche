package com.example.myapplication;

public class ServiceProviderData {

    private String serviceProviderId;
    private String serviceProviderName;
    private String serviceProviderCounty;
    private String serviceProviderSubCounty;
    private int serviceProviderImage;
    private Boolean selected = false;


    public ServiceProviderData(String serviceProviderId, String serviceProviderName, String serviceProviderCounty, String serviceProviderSubCounty, int serviceProviderImage) {

        this.serviceProviderId = serviceProviderId;
        this.serviceProviderName = serviceProviderName;
        this.serviceProviderCounty = serviceProviderCounty;
        this.serviceProviderSubCounty = serviceProviderSubCounty;
        this.serviceProviderImage = serviceProviderImage;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceProviderCounty() {
        return serviceProviderCounty;
    }

    public void setServiceProviderCounty(String serviceProviderCounty) {
        this.serviceProviderCounty = serviceProviderCounty;
    }

    public String getServiceProviderSubCounty() {
        return serviceProviderSubCounty;
    }

    public void setServiceProviderSubCounty(String serviceProviderSubCounty) {
        this.serviceProviderSubCounty = serviceProviderSubCounty;
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

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }
}
