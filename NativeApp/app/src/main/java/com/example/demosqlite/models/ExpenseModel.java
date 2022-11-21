package com.example.demosqlite.models;

import java.util.Date;
import java.util.UUID;

public class ExpenseModel {

    //region $private properties
    private UUID expenseID;
    private String expenseType;
    private int expenseAmount;
    private String expenseTime;
    private String expenseComment;

    //endregion

    //region $Getter and Setter

    public String getExpenseComment() {
        return expenseComment;
    }

    public void setExpenseComment(String expenseComment) {
        this.expenseComment = expenseComment;
    }

    public UUID getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(UUID expenseID) {
        this.expenseID = expenseID;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(int expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(String expenseTime) {
        this.expenseTime = expenseTime;
    }

    //endregion

    //region $Constructor

    public ExpenseModel(UUID expenseID, String expenseType, int expenseAmount, String expenseTime, String expenseComment) {
        this.expenseID = expenseID;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseTime = expenseTime;
        this.expenseComment = expenseComment;
    }

    //endregion

}
