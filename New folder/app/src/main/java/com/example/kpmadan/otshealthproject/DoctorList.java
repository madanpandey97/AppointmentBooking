package com.example.kpmadan.otshealthproject;

/**
 * Created by Madan on 28-10-2017.
 */

public class DoctorList {
    private String doctor_name,location,services,fees,description,image;

    public String getDoctor_name() {
        return doctor_name;
    }

    public DoctorList(){

    }
    public DoctorList(String doctor_name, String location, String services, String fees, String description, String image) {
        this.doctor_name = doctor_name;
        this.location = location;
        this.services = services;
        this.fees = fees;
        this.description = description;
        this.image = image;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
