package kz.stargazer.arkhamhorror_client.Heroes.monsters.factory;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Hunter;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Lurker;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Patrol;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Node;

public class MonsterFactory {
    public Monster createMonster (MonsterType typeGame, Game game, String name, String spawn,
                                  Node space, int damage, int horror, int health) {
        Monster monster = null;

        switch (typeGame) {
            case HUNTER:
                monster = new Hunter(game, name, spawn, space, damage, horror, health);
                monster = (Hunter)monster;
                ((Hunter) monster).setPlayer(game.getPlayers().get(0));
                break;
            case LURKER:
                monster = new Lurker(game, name, spawn, space, damage, horror, health);
                break;
            case PATROL:
                monster = new Patrol(game, name, spawn, space, damage, horror, health);
                break;

        }

        return monster;
    }

}
