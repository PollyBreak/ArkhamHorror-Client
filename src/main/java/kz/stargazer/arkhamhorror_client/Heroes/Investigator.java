package kz.stargazer.arkhamhorror_client.Heroes;

import kz.stargazer.arkhamhorror_client.Assets.Action;
import kz.stargazer.arkhamhorror_client.Assets.Actions;
import kz.stargazer.arkhamhorror_client.Assets.AssetCard;
import kz.stargazer.arkhamhorror_client.Assets.Assets;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class Investigator {
    private Game game;

    private String name;

    private int health;
    private int sanity;
    private int focusLimit;
    private int freeHands = 2;
    private int money;


    private int lore;
    private int influence;
    private int observation;
    private int strength;
    private int will;

    private ArrayList<Integer> successes = new ArrayList<>();

    private Assets assets;
    private int clues = 0;
    private Node space;
    private int actions = 2;
    private int steps = 2;
    private ArrayList<Actions> doneActions = new ArrayList<>();

    private Action talent;

    private boolean delayed;
    private boolean withMonsters;
    private boolean active;
    private boolean ready;

    public Investigator(InvestigatorBuilder investigatorBuilder){
        this.game = investigatorBuilder.getGame();
        this.name = investigatorBuilder.getName();
        this.health = investigatorBuilder.getHealth();
        this.sanity = investigatorBuilder.getSanity();
        this.focusLimit = investigatorBuilder.getFocusLimit();
        this.lore = investigatorBuilder.getLore();
        this.influence = investigatorBuilder.getInfluence();
        this.observation = investigatorBuilder.getObservation();
        this.strength = investigatorBuilder.getStrength();
        this.will = investigatorBuilder.getWill();
        this.space=investigatorBuilder.getStartNode();
        this.assets=investigatorBuilder.getAssets();
        this.talent=investigatorBuilder.getTalent();
        this.money=investigatorBuilder.getMoney();
        delayed = false;
        withMonsters = false;
        ready = false;
        successes.add(5);
        successes.add(6);
    }

    public Investigator(Game game, String name, int health, int sanity, int focusLimit, int lore,
                        int influence, int observation, int strength, int will, Node location,
                        Assets assets, Action talent, int money) {
        this.game = game;
        this.name = name;
        this.health = health;
        this.sanity = sanity;
        this.focusLimit = focusLimit;
        this.lore = lore;
        this.influence = influence;
        this.observation = observation;
        this.strength = strength;
        this.will = will;
        this.space=location;
        this.assets=assets;
        this.talent=talent;
        this.money = money;
        delayed = false;
        withMonsters = false;
        ready = false;
        successes.add(5);
        successes.add(6);
    }


    public void move(Node node){
        
        doAction(Actions.MOVE_ACTION);
    }

    private void doAction(Actions action) {
        doneActions.add(action);
        actions--;
    }


    public ArrayList<Integer> test(int skill){
        ArrayList<Integer> dices = new ArrayList<>();
        for (int i=0; i<skill; i++){
            dices.add((int)(Math.random() * 6) + 1);
        }
        return dices;
    }

    public Game getGame() {
        return game;
    }
    public Node getSpace(){return space;}
    public void setSpace(Node node){
        space = node;
    }
}
