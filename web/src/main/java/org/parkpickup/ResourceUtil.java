package org.parkpickup;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

@Component
public class ResourceUtil {
    public String getSqlStatementFromFile(String sqlFilePath) throws FileNotFoundException, SQLException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(sqlFilePath).getFile());

        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            sb.append(line).append("\n");
        }

        scanner.close();

        return sb.toString();
    }
}
