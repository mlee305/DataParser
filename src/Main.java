public class Main {
    public static void main(String[] args) {
        String data = Utils.readFileAsString("data/2016_Presidential_Results.csv");
        Utils.parse2016PresidentialResults(data);
    }
}
