package kz.stargazer.arkhamhorror_client.Heroes;

import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Node;

public abstract class Monster {
    private String name;
    private String spawn;
    private Node space;
    private int damage;
    private int horror;
    private int health;

    private boolean engaged;
    private boolean exhausted;

    public abstract void play();

    public void ready() {
        exhausted = false;
    }

    public void hit(Investigator player) {
        player.setHealth(player.getHealth()-damage);
        player.setSanity(player.getSanity()-horror);
    }

    public void isEngaged(){
        if (!space.getHeroes().isEmpty()){
            this.engaged = true;
        } else {
            this.engaged = false;
        }
    }

    public void makeTurn() {
        this.isEngaged();
        if (engaged) {
            hit(space.getHeroes().get(0));
        } else {
            play();
        }
    }

    public void move(Node node) {

    }

    /////////// getters setters


    public void setEngaged(boolean engaged) {
        this.engaged = engaged;
    }

    public boolean isExhausted() {
        return exhausted;
    }

    public void setExhausted(boolean exhausted) {
        this.exhausted = exhausted;
    }
}
