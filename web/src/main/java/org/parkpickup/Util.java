package org.parkpickup;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

@Component
public class Util {
    public String createGeographyStringFromLatLng(double[] lats, double[] lngs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lats.length; i++) {
            sb.append(lngs[i]);
            sb.append(" ");
            sb.append(lats[i]);

            if (i != lats.length - 1) {
                sb.append(",");
            }
        }

        return "SRID=4326;LINESTRING(" + sb.toString() + ")";
    }

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
