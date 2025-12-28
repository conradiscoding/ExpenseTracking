package com.expensetracker;

import com.expensetracker.Utility.ExtractFilePath;
import com.expensetracker.controller.ExpenseController;
import com.expensetracker.domain.Expense;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        final ExtractFilePath extractFile = new ExtractFilePath();


        ExpenseController controller = new ExpenseController();
        String output = controller.createExpeseFile("testExpense");
        System.out.println(output);

        String filePath = extractFile.extractFilePath(output);
        System.out.println(filePath);

        ArrayList<Expense> expenses = controller.getExpenses(filePath);
        for (Expense expense : expenses) {
            System.out.println(expense.getName());
        }


    }
}
