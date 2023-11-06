package kz.stargazer.arkhamhorror_client.Heroes.monsters;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Node;

public class Hunter extends Monster {
    private Investigator player;
    @Override
    public void play() {
        move(player.getSpace());
    }

    @Override
    public void update(Game game) {
    }

    public Hunter(Game game, String name, String spawn, Node space, int damage, int horror, int health) {
        super(game, name, spawn, space, damage, horror, health);
    }

    public Hunter(Game game, String name, String spawn, Node space, int damage, int horror, int health,
                  Investigator player) {
        super(game, name, spawn, space, damage, horror, health);
        this.player = player;
    }

    public Investigator getPlayer() {
        return player;
    }

    public void setPlayer(Investigator player) {
        this.player = player;
    }

}
