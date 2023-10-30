package kz.stargazer.arkhamhorror_client.Assets.actionStrategies;

import kz.stargazer.arkhamhorror_client.Assets.Action;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

public class GroundPepper implements Action {
    @Override
    public void use(Investigator investigator) {
        if (investigator.isWithMonsters()){
            investigator.getSpace().getMonsters().get(0).setExhausted(true);
        }
    }
}
