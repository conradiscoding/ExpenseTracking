package com.expensetracker.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Expense {
    private Integer expenseId;
    private String name;
    private String category;
    private Double amount;
    private String date;

    public Expense(String name, String category, Double amount, String date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public Expense(String name, String category, Double amount) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = LocalDateTime.now().format(formatter);
    }
    public Integer getexpenseId() {return expenseId;}
    public String getName() {return name;}
    public String getCategory() {return category;}
    public Double getAmount() {return amount;}
    public String getDate() {return date;}
    public void setexpenseId(Integer expenseId) {this.expenseId = expenseId;}
    public void setName(String name) {this.name = name;}
    public void setCategory(String category) {this.category = category;}
    public void setAmount(Double amount) {this.amount = amount;}
    public void setDate(String date) {this.date = date;}

}
