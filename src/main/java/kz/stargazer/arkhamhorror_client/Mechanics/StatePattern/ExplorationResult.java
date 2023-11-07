package kz.stargazer.arkhamhorror_client.Mechanics.StatePattern;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

import java.util.ArrayList;

public class ExplorationResult extends ActionResult{

    @Override
    public void act() {
        int successes = player.countSuccesses();
        player.getGame().getPlot().setClues(player.getGame().getPlot().getClues()+successes);
        player.setActionResult(nextResult);
        player.setLastTest(new ArrayList<>());
    }

    public ExplorationResult(Investigator player, ActionResult nextResult) {
        super(player, nextResult);
    }
}
