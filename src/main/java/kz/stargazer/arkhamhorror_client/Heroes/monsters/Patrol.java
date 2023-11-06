package kz.stargazer.arkhamhorror_client.Heroes.monsters;

import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Node;

public class Patrol extends Monster {
    private Node destination;

    @Override
    public void update(Game game) {
           this.destination = game.getUnstableSpace();
    }

    @Override
    public void play() {
        move(destination);
    }

    public Patrol(Game game, String name, String spawn, int damage, int horror, int health) {
        super(game, name, spawn, damage, horror, health);
    }

}
