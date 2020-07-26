package com.example.travelapp.recyclerviewImages;


public class Trip {
    private String location;
    private String description;
    private String imageUrl;
    private String user;
    public Trip(){

    }

    public Trip(String location, String description, String imageUrl, String user) {
        this.location = location;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
