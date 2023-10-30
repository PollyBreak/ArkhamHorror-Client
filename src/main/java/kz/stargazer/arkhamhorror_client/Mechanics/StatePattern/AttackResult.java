package kz.stargazer.arkhamhorror_client.Mechanics.StatePattern;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

public class AttackResult extends ActionResult {
    @Override
    public void act() {
        
    }

    public AttackResult(Investigator player, ActionResult nextResult) {
        super(player, nextResult);
    }
}
