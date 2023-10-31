package kz.stargazer.arkhamhorror_client.Mechanics.StatePattern;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.Monster;

public class EvadeResult extends ActionResult{
    private Monster monster;

    @Override
    public void act() {
        int successes = player.countSuccesses();
        if (successes > 0) {
            monster.setExhausted(true);
            player.checkIfMonster();
        }
        player.setActionResult(nextResult);
    }

    public EvadeResult(Investigator player, ActionResult nextResult, Monster monster) {
        super(player, nextResult);
        this.monster = monster;
    }


}
