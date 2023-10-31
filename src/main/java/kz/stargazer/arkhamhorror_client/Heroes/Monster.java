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
    private Investigator goal;
    private Game game;
    private boolean engaged;
    private boolean exhausted;

    public Monster(Game game, String name, String spawn, Node space, int damage, int horror, int health) {
        this.game = game;
        this.name = name;
        this.spawn = spawn;
        this.space = space;
        this.damage = damage;
        this.horror = horror;
        this.health = health;
        checkEngaged();
        exhausted = false;
        game.getMonsters().add(this);
    }

    public abstract void play();

    public void ready() {
        exhausted = false;
    }

    public void hit(Investigator player) {
        player.setHealth(player.getHealth()-damage);
        player.setSanity(player.getSanity()-horror);
    }

    public void checkEngaged(){
        if (!space.getHeroes().isEmpty()){
            this.engaged = true;
            this.goal = space.getHeroes().get(0);
        } else {
            this.engaged = false;
            this.goal = null;
        }
    }

    public void makeTurn() {
        if (isExhausted()){
            exhausted = false;
            checkEngaged();
        } else {
            if (engaged) {
                hit(goal);
            } else {
                play();
            }
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

    public boolean isEngaged() {
        return engaged;
    }
    public void setSpace(Node space){this.space = space;}

    public Node getSpace() { return space;}

    public Game getGame() {
        return game;
    }
}
