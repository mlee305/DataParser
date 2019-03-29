import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        String data = Utils.readFileAsString("data/2016_Presidential_Results.csv");
//        System.out.println(Utils.parseDepressionData(data));
        String educationData = Utils.readFileAsString("data/Education.csv");
        String employmentData = Utils.readFileAsString("data/Unemployment.csv");
        ArrayList<ElectionResult> results = Utils.parse2016PresidentialResults(data);
        DataManager dataManager = Utils.parseEducationEmploymentElectionData(educationData, employmentData, data);
        List<State> states = dataManager.getStates();
        int count = 0;
        System.out.println("State Name, County Name, County Fips, % No High School, % Only High School, % Some College, % Bachelors Or More, " +
                "Total Labor Force, Employed Labor Force, Unemployed Labor Force, Unemployed Percent");

        for (State state : states) {
            List<County> counties = state.getCounties();
            for (County county : counties) {
                try {
                    System.out.println(state.getName() + "," + county.getName() + "," + county.getFips() + "," + county.getEduc2016().getNoHighSchool()
                            + "," + county.getEduc2016().getOnlyHighSchool() + "," + county.getEduc2016().getSomeCollege()
                            + "," + county.getEduc2016().getBachelorsOrMore() + "," + county.getEmploy2016().getTotalLaborForce() + "," +
                            county.getEmploy2016().getEmployedLaborForce() + "," + county.getEmploy2016().getUnemployedLaborForce()
                            + "," + county.getEmploy2016().getUnemployedPercent() + ",");
                } catch (NullPointerException e){
//                    System.out.println(state);
//                    System.out.println(county);
//
                    e.printStackTrace();
                }
            }
        }
    }
}
