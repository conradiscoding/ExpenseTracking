package com.expensetracker;

import com.expensetracker.controller.ExpenseController;
import com.expensetracker.domain.Expense;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ExpenseController controller = new ExpenseController();
        int selection = 0;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Hello, welcome to the Expense Tracker");

        while(selection != 7) {
            System.out.println("What would you like to do?");
            System.out.println("1. Create New Expense File");
            System.out.println("2. Load Expense File");
            System.out.println("3. Add Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Update Expense");
            System.out.println("6. Total Expenses by Category");
            System.out.println("7. Exit Program");

            selection = Integer.parseInt(reader.readLine());


            switch (selection) {
                case 1:
                    System.out.println("Enter Name for New Expense File");
                    String name = reader.readLine();
                    if (name.isBlank() || name.length() < 3) {
                        System.out.println("returning back to menu");
                        selection = 0;
                        break;
                    }
                    String output = controller.createExpeseFile(name);
                    System.out.println(output);
                    break;
                case 2:
                    System.out.println("Enter File Path for Load Expense File");
                    String path = reader.readLine();
                    if (path.isBlank() || path.length() < 3) {
                        System.out.println("returning back to menu");
                        selection = 0;
                        break;
                    }
                    ArrayList<Expense> expenses = controller.getExpenses(path);
                    for (Expense expense : expenses) {
                        System.out.println(expense.getexpenseId() + " " + expense.getName() + " " + expense.getCategory() + " " + expense.getAmount());
                    }
                    break;
                case 3:
                    System.out.println("Enter File Path for Add Expense File");
                    String addPath = reader.readLine();
                    if (addPath.isBlank()) {
                        System.out.println("returning back to menu");
                        selection = 0;
                        break;
                    }
                    System.out.println("Enter Expense Name, Expense Category, Expense Amount");
                    String addExpenseName = reader.readLine();
                    String[] values = addExpenseName.split(" ");
                    controller.addExpense(values[0], values[1], Double.parseDouble(values[2]), addPath);
                    break;

                case 4:
                    System.out.println("Enter File Path for Delete Expense File");
                    String deletePath = reader.readLine();
                    if (deletePath.isBlank()) {
                        System.out.println("returning back to menu");
                        selection = 0;
                        break;
                    }
                    ArrayList<Expense> expensesToDelete = controller.getExpenses(deletePath);
                    for (Expense expense : expensesToDelete) {
                        System.out.println(expense.getexpenseId() + " " + expense.getName() + " " + expense.getCategory() + " " + expense.getAmount());
                    }
                    System.out.println("Enter Id to delete");
                    int id = Integer.parseInt(reader.readLine());
                    if(id < 0 || id > expensesToDelete.size()) {
                        System.out.println("Can't find id. returning back to menu");
                        selection = 0;
                    }
                    controller.deleteExpenseEntry(deletePath, id);
                    break;

                case 5:
                    System.out.println("Enter File Path for Update Expense File");
                    String updatePath = reader.readLine();
                    if (updatePath.isBlank()) {
                        System.out.println("returning back to menu");
                        selection = 0;
                        break;
                    }
                    ArrayList<Expense> expeseOptions = controller.getExpenses(updatePath);
                    for (Expense expense : expeseOptions) {
                        System.out.println(expense.getexpenseId() + " " + expense.getName() + " " + expense.getCategory() + " " + expense.getAmount());
                    }
                    System.out.println("Please provide the expense Id you want to update");
                    id = Integer.parseInt(reader.readLine());
                    System.out.println("Please provide new Name, Category, Expense Amount");
                    String newExpense = reader.readLine();
                    controller.updateExpense(updatePath, id, newExpense);
                    break;

                case 6:
                    System.out.println("Enter File Path to calculate total Expenses");
                    String filepath = reader.readLine();
                    ArrayList<Expense> ex = controller.getExpenses(filepath);
                    Map<String,Double> categoryExpenses = ExpenseController.calculateTotalExpensesByCategory(ex);

                    categoryExpenses.forEach((category, totalExpense) -> {
                        System.out.println(category + " " + totalExpense);
                    });
                    break;

                case 7:
                    selection = 7;
                    break;

                default:
                    System.out.println("returning back to menu");
                    selection = 0;
                    break;
            }
        }

    }
}
