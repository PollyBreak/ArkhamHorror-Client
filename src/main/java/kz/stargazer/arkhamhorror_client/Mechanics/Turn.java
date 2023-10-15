package kz.stargazer.arkhamhorror_client.Mechanics;


//import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
//
//import java.util.ArrayList;
//
//public class Turn {
//    private Investigator currentInvestigator;
//    private TestResult testResult = null;
//    private TestResult tempTestResult;
//
//    public ArrayList<Integer> test(String skill, Investigator investigator){
//        ArrayList<Integer> dices = new ArrayList<>();
//        int success = 0;
//        for (int i=0; i<investigator.getSkills().get(skill); i++){
//            int dice = (int)(Math.random() * 6) + 1;
//            if (investigator.getSuccesses().contains(dice)){
//                success++;
//            }
//            dices.add(dice);
//        }
//        tempTestResult.setSuccess(success);
//        return dices;
//    }
//
//    public TestResult finishTest(){
//        tempTestResult.checkFail();
//        testResult = tempTestResult;
//        tempTestResult.setSuccess(0);
//        return testResult;
//    }
//
//}
