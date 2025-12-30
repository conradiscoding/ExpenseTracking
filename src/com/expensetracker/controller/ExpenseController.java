package com.expensetracker.controller;

import com.expensetracker.domain.Expense;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ExpenseController {
    private static final String HEADER = "Expense\tCategory\tAmount";

    public ArrayList<Expense> getExpenses(String filePath) {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip the first line (header)
            if ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            // Read remaining lines
            while ((line = br.readLine()) != null) {
                String[] values = line.trim().split("\\s+");

                // Skip empty lines
                if (values.length == 0 || (values.length == 1 && values[0].isEmpty()) || values.length != 3) {
                    continue;
                }

                Expense expense = new Expense(values[0], values[1], Double.parseDouble(values[2]));
                expenses.add(expense);

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expenses;
    }

    public void addExpense(String name, String category, double amount, String filePath) {
        if (name == null || name.isEmpty() || category == null || category.isEmpty() || amount < 0 || filePath == null || filePath.isEmpty()) {
            return;
        }
        String text = name + "\t" + category + "\t" + amount;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.newLine(); // Insert a platform-independent newline
            writer.write(text);
            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public String createExpeseFile(String fileName) throws IOException {
        String filePath = fileName + ".txt";
        File file = new File(filePath);
        if (file.createNewFile()) {
            if (file.length() == 0) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    writer.write(HEADER);
                    writer.newLine();
                }
            }
            return "File created " + file.getAbsolutePath();
        } else {
            return "File already exists " + file.getAbsolutePath();
        }
    }
}
