package kz.stargazer.arkhamhorror_client.Mechanics.StatePattern;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;

public class AttackResult extends ActionResult {

    private Monster monster;

    @Override
    public void act() {
        int successes = player.countSuccesses();
        monster.setHealth(monster.getHealth()-successes);
        if (monster.getHealth()<1) {
            player.getGame().getMonsters().remove(monster);
            player.getSpace().getMonsters().remove(monster);
            player.getGluedMonsters().remove(monster);
        }
    }

    public AttackResult(Investigator player, ActionResult nextResult, Monster monster) {
        super(player, nextResult);
        this.monster = monster;
    }
}
