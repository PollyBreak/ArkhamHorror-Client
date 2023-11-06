package kz.stargazer.arkhamhorror_client.Heroes.monsters;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Node;

public class Lurker extends Monster {
    private Investigator player;
    @Override
    public void play() {
        getSpace().addDoom();
    }

    @Override
    public void update(Game game) {
    }

    public Lurker(Game game, String name, String spawn, int damage, int horror, int health) {
        super(game, name, spawn, damage, horror, health);
    }


}
