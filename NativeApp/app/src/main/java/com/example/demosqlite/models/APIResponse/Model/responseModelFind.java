package com.example.demosqlite.models.APIResponse.Model;

import com.example.demosqlite.models.APIRequest.Model.ModelExpenseRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class responseModelFind {
    private String _id;
    private UUID tripID;
    private String tripName;
    private String tripDest;
    private Date tripStartDate;
    private Date tripEndDate;
    private boolean isRiskAssessmentChecked;
    private String tripDesc;
    private byte[] tripImage;

    private ArrayList<ModelExpenseRequest> listExpenses;
    private Date createdDate;
    private Date updatedDate;

    //region $getter and setter

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Date getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(Date tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public Date getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(Date tripEndDate) {
        this.tripEndDate = tripEndDate;
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

    public byte[] getTripImage() {
        return tripImage;
    }

    public void setTripImage(byte[] tripImage) {
        this.tripImage = tripImage;
    }

    public ArrayList<ModelExpenseRequest> getListExpenses() {
        return listExpenses;
    }

    public void setListExpenses(ArrayList<ModelExpenseRequest> listExpenses) {
        this.listExpenses = listExpenses;
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

    //endregion

    //region $constructor

    public responseModelFind(String _id, UUID tripID, String tripName, String tripDest, Date tripStartDate, Date tripEndDate, boolean isRiskAssessmentChecked, String tripDesc, byte[] tripImage, ArrayList<ModelExpenseRequest> listExpenses, Date createdDate, Date updatedDate) {
        this._id = _id;
        this.tripID = tripID;
        this.tripName = tripName;
        this.tripDest = tripDest;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.isRiskAssessmentChecked = isRiskAssessmentChecked;
        this.tripDesc = tripDesc;
        this.tripImage = tripImage;
        this.listExpenses = listExpenses;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    //endregion
}
