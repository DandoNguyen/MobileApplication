package com.example.demosqlite.models;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

public class TripModel {

    //region $Private Properties
    private UUID tripID;
    private String tripName;
    private String tripDest;
    private String tripStartDate;
    private String tripEndDate;
    private boolean isRiskAssessmentChecked;
    private String tripDesc;
    private byte[] tripImage;
    private String tripImageFilePath;

    private Date createdDate = new Date(System.currentTimeMillis());
    private Date updatedDate = new Date(System.currentTimeMillis());
    //endregion

    //region $Getters and Setters


    public String getTripImageFilePath() {
        return tripImageFilePath;
    }

    public void setTripImageFilePath(String tripImageFilePath) {
        this.tripImageFilePath = tripImageFilePath;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public UUID getTripID() {
        return tripID;
    }

    public void setTripID(UUID tripID) {
        this.tripID = tripID;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDest() {
        return tripDest;
    }

    public void setTripDest(String tripDest) {
        this.tripDest = tripDest;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public boolean isRiskAssessmentChecked() {
        return isRiskAssessmentChecked;
    }

    public void setRiskAssessmentChecked(boolean riskAssessmentChecked) {
        isRiskAssessmentChecked = riskAssessmentChecked;
    }

    public String getTripDesc() {
        return tripDesc;
    }

    public void setTripDesc(String tripDesc) {
        this.tripDesc = tripDesc;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(String tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public byte[] getTripImage() {
        return tripImage;
    }

    public void setTripImage(byte[] tripImage) {
        this.tripImage = tripImage;
    }

    //endregion

    //region $Constructor
    public TripModel(UUID tripID, String tripName, String tripDest, String tripStartDate, String tripEndDate, boolean isRiskAssessmentChecked, String tripDesc, byte[] tripImage) {
        this.tripID = tripID;
        this.tripName = tripName;
        this.tripDest = tripDest;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.isRiskAssessmentChecked = isRiskAssessmentChecked;
        this.tripDesc = tripDesc;
        this.tripImage = tripImage;
    }

    //endregion
}
