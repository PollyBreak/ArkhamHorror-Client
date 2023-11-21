package kz.stargazer.arkhamhorror_client.Mechanics.StatePattern;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;

public class AttackResult extends ActionResult {
    Monster monster;

    @Override
    public void act() {
        int successes = player.countSuccesses();
        monster.setHealth(monster.getHealth()-successes);
        player.getGame().getFX().leaveMessage("Player has hit "+monster.getName()+" with "+successes+" damage and monster is left with "+monster.getHealth()+" health");
        if (monster.getHealth() < 1) {
            player.getGame().getMonsters().remove(monster);
            player.getGluedMonsters().remove(monster);
            player.getSpace().getMonsters().remove(monster);
            player.getGame().getFX().removeMonsterFromHand(monster);
        }
        player.setActionResult(nextResult);
    }

    public AttackResult(Investigator player, ActionResult nextResult, Monster monster) {
        super(player, nextResult);
        this.monster = monster;
    }
}
