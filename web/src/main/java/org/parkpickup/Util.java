package org.parkpickup;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

@Component
public class Util {
    public String createGeometryStringFromLatLng(double[] lats, double[] lngs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lats.length; i++) {
            sb.append(lngs[i]);
            sb.append(" ");
            sb.append(lats[i]);

            if (i != lats.length - 1) {
                sb.append(",");
            }
        }

        return "POLYGON((" + sb.toString() + "))";
    }

    public String getSqlStatementFromFile(String sqlFilePath) throws IOException, SQLException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(sqlFilePath).getFile());

        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        bufferedReader.close();

        return sb.toString();
    }
}
