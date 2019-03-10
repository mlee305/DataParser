import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        String data = Utils.readFileAsString("data/2016_Presidential_Results.csv");
        String educationData = Utils.readFileAsString("data/Education.csv");
        String employmentData = Utils.readFileAsString("data/Unemployment.csv");
        ArrayList<ElectionResult> results = Utils.parse2016PresidentialResults(data);
        DataManager dataManager = Utils.parseEducationEmploymentElectionData(educationData, employmentData, data);
        List<State> states = dataManager.getStates();
        for (State state : states) {
            List<County> counties = state.getCounties();
            for (County county : counties) {
                System.out.println("State: " + state.getName() + "\n"
                + "County: " + county.getName() + "\n"
                + "Fips: " + county.getFips());
            }
        }
    }
}
