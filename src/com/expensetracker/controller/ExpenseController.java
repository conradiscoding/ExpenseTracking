package com.expensetracker.controller;

import com.expensetracker.domain.Expense;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class ExpenseController {
    private static final String HEADER = "ExpenseID\t Expense\t Category\t Amount";

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
                if (values.length == 0 || (values.length == 1 && values[0].isEmpty()) || values.length != 4) {
                    continue;
                }

                Expense expense = new Expense(Integer.parseInt(values[0]),values[1], values[2], Double.parseDouble(values[3]));
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

        Integer currentId = getExpenses(filePath).size() + 1;
        String text =  currentId + "\t" + name + "\t" + category + "\t" + amount;
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

    public void deleteExpenseEntry(String filePath, int id) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        if (!inputFile.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;

            // Read and write the header line (first line)
            if ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }

            // Process remaining lines
            while ((line = br.readLine()) != null) {
                if (!line.trim().startsWith(id + "\t")) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Replace original file with updated file
        Files.delete(inputFile.toPath());
        Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Entry with ID " + id + " removed (if it existed).");
    }

    public static Map<String, Double> calculateTotalExpensesByCategory(ArrayList<Expense>  expenses) {
        Map<String, Double> totalExpenses = new HashMap<>();

        for (Expense expense : expenses) {
            if (expense.getCategory() == null) continue; // skip null categories

            // Get current total or 0.0 if not present
            double currentTotal = totalExpenses.getOrDefault(expense.getCategory(), 0.0);

            // Add the new amount (skip null amounts if applicable)
            totalExpenses.put(expense.getCategory(), currentTotal + expense.getAmount());
        }
        return totalExpenses;
    }
}
