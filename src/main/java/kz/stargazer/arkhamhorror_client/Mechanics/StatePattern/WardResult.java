package kz.stargazer.arkhamhorror_client.Mechanics.StatePattern;

import kz.stargazer.arkhamhorror_client.Assets.Actions;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;

import java.util.ArrayList;

public class WardResult extends ActionResult{

    @Override
    public void act() {
        int successes = player.countSuccesses();
        int count = 0;
        for (int i=0; i< successes; i++) {
            if (player.getSpace().getDoom() > 0){
                player.getSpace().setDoom(player.getSpace().getDoom() - 1);
                count++;
            }
        }
        if (count > 1) {
            player.setClues(player.getClues()+1);
        }
        player.setActionResult(nextResult);
    }

    public WardResult(Investigator player, ActionResult nextResult) {
        super(player, nextResult);
    }
}
