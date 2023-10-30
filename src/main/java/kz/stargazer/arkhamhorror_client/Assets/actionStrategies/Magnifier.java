package kz.stargazer.arkhamhorror_client.Assets.actionStrategies;

import kz.stargazer.arkhamhorror_client.Assets.Action;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

public class Magnifier implements Action {
    private int plus;
    @Override
    public void use(Investigator investigator) {
        investigator.test(plus);
    }
}
