package io.github.dhar135.expense_tracker.util;

import io.github.dhar135.expense_tracker.expense.model.Expense;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class is responsible for exporting the list of expenses to a CSV file.
 */
@Component
public class CSVExporter {

    /**
     * Exports the list of expenses to a CSV file.
     *
     * @param expenses The list of expenses to export.
     * @param fileName The name of the file to export to.
     * @return true if export was successful, false otherwise.
     */
    public static boolean exportToCSV(List<Expense> expenses, String fileName) {
        if (expenses == null || fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        Path path = Paths.get(fileName);

        try (FileWriter writer = new FileWriter(path.toFile())){
            // Write the header
            writer.write("ID,Description,Amount,Date,Category\n");

            // Write the expenses
            for (Expense expense : expenses) {
                writer.append(String.valueOf(expense.getId())).append(",");
                writer.append(expense.getDescription()).append(",");
                writer.append(expense.getAmount().toString()).append(",");
                writer.append(expense.getDate().toString()).append(",");
                writer.append(expense.getCategory().toString()).append("\n");
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Error while exporting expenses to CSV: " + e.getMessage());
            return false;
        }

    }
}
