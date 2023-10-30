package kz.stargazer.arkhamhorror_client.Assets;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

import java.util.ArrayList;

public class Item extends AssetCard{
    private int value;
    private int hands;
    private int health;
    private int sanity;
    private ArrayList<Integer> traits;

    private boolean alive = true;

    public void takeDamage(int damage) {
        this.health -= damage;
        isAlive();
    }

    public void isAlive(){
        if (health < 0) {
            alive = false;
        }
    }

}
