package kz.stargazer.arkhamhorror_client.Mechanics;

import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;

public class MonsterPhaseLogic {

    public void runPhase(Game game) {
        for (Monster monster:game.getMonsters()){
            monster.makeTurn();
        }
        game.setCurrentPhase(Phases.ACTION_PHASE);
    }
}
