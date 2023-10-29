package kz.stargazer.arkhamhorror_client.Heroes;

import kz.stargazer.arkhamhorror_client.brd.Node;

public class Patrol extends Monster{
    private Node destination;

    @Override
    public void play() {
        move(destination);
    }
}
