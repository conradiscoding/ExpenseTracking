package com.expensetracker.Utility;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractFilePath {
    private static final String FILE_PATH_REGEX =
            "([a-zA-Z]:\\\\(?:[^\\\\/:*?\"<>|\\r\\n]+\\\\)*[^\\\\/:*?\"<>|\\r\\n]+\\.[\\w]+)|" + // Windows
                    "(/(?:[^/\0]+/)*[^/\0]+\\.[\\w]+)";

    public String extractFilePath(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        Pattern pattern = Pattern.compile(FILE_PATH_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group();
        }
        return null; // No file path found
    }
}
