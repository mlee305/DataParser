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
            rows[i] = modifyString(rows[i]);
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

    public static String modifyString(String input) {
        boolean again;
        do {
            again = false;
            int index1 = input.indexOf("\"");
            int index2 = input.indexOf("\"", index1 + 1);
            int commaIndex = input.indexOf(",", index1);
            if (commaIndex > index1 && commaIndex < index2) {
                input = input.substring(0, commaIndex) + input.substring(commaIndex + 1);
                again = true;
            }
        } while (again);
        input = input.substring(0, input.indexOf("%")) + input.substring(input.indexOf("%") + 1);
        return input;
    }

    public static DataManager parseEducationEmploymentElectionData(String educationData, String employmentData, String electionData) {
        DataManager dataManager = new DataManager();
        String[] educationLines = educationData.split("\n");
        createStates(educationLines, dataManager);
        addCountiesToState(educationLines, dataManager, employmentData, electionData);

        // percent of adults with less than a high school diploma: index 11

        return dataManager;
    }

    public static void addCountiesToState(String[] lines, DataManager dataManager, String employmentData, String electionData) {
        for (int i = 6; i < 3288; i++){
            String currentLine = removeExtraSpacesAndCommasInQuotes(lines[i]);
            String[] components = currentLine.split(",");
            State currentState = dataManager.getState(components[1]);
            County currentCounty =  new County(components[2], Integer.parseInt(components[0]));
            if (components.length < 45) {
                continue;
            }
            Education2016 education2016 = new Education2016(Double.parseDouble(components[43]), Double.parseDouble(components[44]),
                    Double.parseDouble(components[45]), Double.parseDouble(components[46]));
            currentCounty.setEduc2016(education2016);
            Employment2016 employment2016 = getEmployment2016(employmentData, i + 3);
            currentCounty.setEmploy2016(employment2016);
            Election2016 election2016 = getElection2016(electionData, currentCounty.getName());
            currentCounty.setVote2016(election2016);
            currentState.addCounty(currentCounty);
        }
    }

    public static Election2016 getElection2016(String electionData, String countyName) {
        ArrayList<ElectionResult> electionResults = parse2016PresidentialResults(electionData);
        if (!contains(electionResults, countyName)) {
            return null;
        }
        ElectionResult currentElectionResult = getCurrentElectionResult(electionResults, countyName);
        Election2016 election2016 = new Election2016(currentElectionResult.getVotes_dem(), currentElectionResult.getVotes_gop(), currentElectionResult.getTotal_votes());
        return election2016;
    }

    public static boolean contains(ArrayList<ElectionResult> electionResults, String countyName) {
        for (ElectionResult electionResult : electionResults) {
            if (electionResult.getCounty_name().equals(countyName)) {
                return true;
            }
        }
        return false;
    }

    public static ElectionResult getCurrentElectionResult(ArrayList<ElectionResult> electionResults, String countyName) {
        for (ElectionResult electionResult : electionResults) {
            if (electionResult.getCounty_name().equals(countyName)) {
                return electionResult;
            }
        }
        return null;
    }

    public static Employment2016 getEmployment2016(String employmentData, int index) {
        String[] employmentLines = employmentData.split("\n");
        if (index > employmentLines.length -1) {
            return null;
        }
        String currentLine = removeExtraSpacesAndCommasInQuotes(employmentLines[index]);
        String[] components = currentLine.split(",");
        System.out.println("Employment line: " + currentLine);
        if (components.length < 45) {
            return null;
        }
        Employment2016 employment2016 = new Employment2016(Integer.parseInt(components[42].substring(1, components[42].length()-1).trim()), Integer.parseInt(components[43].substring(1, components[43].length()-1).trim()), Integer.parseInt(components[44].substring(1, components[44].length()-1).trim()), Double.parseDouble(components[45].trim()));
        return employment2016;
    }

    public static void createStates(String[] lines, DataManager dataManager) {
        for (int i = 6; i < 3288; i++) {
            String currentLine = removeExtraSpacesAndCommasInQuotes(lines[i]);
            String[] components = currentLine.split(",");
            System.out.println(components[1] + ", " + i);
            if (!dataManager.containsState(components[1])) {
                State newState = new State(components[1]);
                dataManager.addState(newState);
            }
        }
    }

    public static String removeExtraSpacesAndCommasInQuotes(String input) {
        boolean inQuotes = false;
        for (int i = 0; i < input.length(); i++) {
            if (input.substring(i, i+1).equals("\"")){
                inQuotes = !inQuotes;
            }
            if (inQuotes && input.substring(i, i+1).equals(",") || inQuotes && input.substring(i, i+1).equals("\t")
                    || inQuotes && input.substring(i, i+1).equals(" ")) {
                input = input.substring(0, i) + input.substring(i+1);
                i--;
            }

        }
        return input;
    }

}
