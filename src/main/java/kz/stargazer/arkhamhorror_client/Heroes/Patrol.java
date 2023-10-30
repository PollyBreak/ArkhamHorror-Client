package kz.stargazer.arkhamhorror_client.Heroes;

import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.Mechanics.Subscriber;
import kz.stargazer.arkhamhorror_client.brd.Node;

public class Patrol extends Monster implements Subscriber {
    private Node destination;

    @Override
    public void update(Game game) {
           this.destination = game.getUnstableSpace();
    }

    @Override
    public void play() {
        move(destination);
    }

}
