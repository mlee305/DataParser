import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        String data = Utils.readFileAsString("data/DepressionData.csv");
        System.out.println(Utils.parseDepressionData(data));
//        String educationData = Utils.readFileAsString("data/Education.csv");
//        String employmentData = Utils.readFileAsString("data/Unemployment.csv");
//        ArrayList<ElectionResult> results = Utils.parse2016PresidentialResults(data);
//        DataManager dataManager = Utils.parseEducationEmploymentElectionData(educationData, employmentData, data);
//        List<State> states = dataManager.getStates();
//        int count = 0;
//        for (State state : states) {
//            List<County> counties = state.getCounties();
//            for (County county : counties) {
//                count++;
//                System.out.println("State: " + state.getName() + "\n"
//                + "County: " + county.getName() + "\n"
//                + "Fips: " + county.getFips() + "\n");
//            }
//        }
//        System.out.println("counties: " + count);
//
//        for (State state : states) {
//            System.out.println(state.getName());
//        }
//
//        System.out.println("states: " + states.size());
    }
}
