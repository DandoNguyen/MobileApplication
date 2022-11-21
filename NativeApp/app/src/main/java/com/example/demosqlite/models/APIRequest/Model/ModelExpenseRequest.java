package com.example.demosqlite.models.APIRequest.Model;

public class ModelExpenseRequest {

    //region $fields
    private String expenseType;
    private int expenseAmount;
    private String expenseTime;
    private String expenseComment;
    //endregion

    //region $Getter Setter

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

    public String getExpenseComment() {
        return expenseComment;
    }

    public void setExpenseComment(String expenseComment) {
        this.expenseComment = expenseComment;
    }

    //endregion

    //region $Constructor

    public ModelExpenseRequest(String expenseType, int expenseAmount, String expenseTime, String expenseComment) {
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseTime = expenseTime;
        this.expenseComment = expenseComment;
    }

    //endregion

}
