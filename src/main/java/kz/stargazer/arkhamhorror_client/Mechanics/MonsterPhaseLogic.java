package kz.stargazer.arkhamhorror_client.Mechanics;

import kz.stargazer.arkhamhorror_client.Heroes.Monster;

import java.util.ArrayList;

public class MonsterPhaseLogic {

    public void runPhase(Game game) {
        for (Monster monster:game.getMonsters()){
            monster.makeTurn();
        }
        game.setCurrentPhase(Phases.ACTION_PHASE);
    }
}
