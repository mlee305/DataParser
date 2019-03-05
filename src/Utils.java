import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
    public static String readFileAsString(String filepath) {
        StringBuilder output = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filepath))) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static ArrayList<ElectionResult> parse2016PresidentialResults (String data) {
        ArrayList<ElectionResult> results = new ArrayList<>();
        String[] rows = data.split("\n");
        for (int i = 1; i < rows.length; i++) {
            ElectionResult electionResult = new ElectionResult();

            boolean again;
            do {
                again = false;
                int index1 = rows[i].indexOf("\"");
                int index2 = rows[i].indexOf("\"", index1 + 1);
                int commaIndex = rows[i].indexOf(",", index1);
                if (commaIndex > index1 && commaIndex < index2) {
                    rows[i] = rows[i].substring(0, commaIndex) + rows[i].substring(commaIndex + 1);
                    again = true;
                }
            } while (again);
            rows[i] = rows[i].substring(0, rows[i].indexOf("%")) + rows[i].substring(rows[i].indexOf("%") + 1);
            String[] parts = rows[i].split(",");


            electionResult.setVotes_dem(Double.parseDouble(parts[1]));
            electionResult.setVotes_gop(Double.parseDouble(parts[2]));
            electionResult.setTotal_votes(Double.parseDouble(parts[3]));
            electionResult.setPer_dem(Double.parseDouble(parts[4]));
            electionResult.setPer_gop(Double.parseDouble(parts[5]));
            electionResult.setDiff(parts[6]);
            electionResult.setPer_point_diff(Double.parseDouble(parts[7]));
            electionResult.setState_abbr(parts[8]);
            electionResult.setCounty_name(parts[9]);
            electionResult.setCombined_fips(Integer.parseInt(parts[10]));
            results.add(electionResult);
        }
        return results;
    }
}
