package kz.stargazer.arkhamhorror_client.Assets.actionStrategies;

import kz.stargazer.arkhamhorror_client.Assets.Action;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

import java.util.ArrayList;

public class ShotgunStrategy implements Action {
    private int damage;
    @Override
    public void use(Investigator investigator) {
        investigator.test(damage);
        int countExtra = 0;
        for (int i=0; i<investigator.getLastTest().size(); i++){
            if (investigator.getLastTest().get(i) == 6) {
                countExtra++;
            }
        }
        for (int i=0; i<countExtra; i++){
            investigator.getLastTest().add(6);
        }
    }
}
