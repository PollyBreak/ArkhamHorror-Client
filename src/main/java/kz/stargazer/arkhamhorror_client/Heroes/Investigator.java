package kz.stargazer.arkhamhorror_client.Heroes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import kz.stargazer.arkhamhorror_client.Assets.Action;
import kz.stargazer.arkhamhorror_client.Assets.Actions;
import kz.stargazer.arkhamhorror_client.Assets.Assets;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Node;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private ArrayList<Integer> lastTest = new ArrayList<>();

    private Action talent;

    private boolean delayed;
    private boolean withMonsters;
    private boolean active;
    private boolean ready;
    private boolean alive;

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



    private void doAction(Actions action) {
        doneActions.add(action);
        actions--;
    }


    public ArrayList<Integer> test(int skill){
        ArrayList<Integer> dices = new ArrayList<>();
        for (int i=0; i<skill; i++){
            int dice = (int)(Math.random() * 6) + 1;
            dices.add(dice);
            lastTest.add(dice);
        }
        return dices;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        isAlive();
    }

    public boolean move(Node destination) {
        int maxDistance = 4;
        Queue<Node> queue = new LinkedList<>();
        queue.add(getSpace());
        int distance = 0;
        boolean found = false;
        HashSet<Node> visitedNodes = new HashSet<>();
        visitedNodes.add(space);

        while (!queue.isEmpty() && distance <= maxDistance) {
            int nodesAtCurrentLevel = queue.size();
            distance++;

            for (int i = 0; i < nodesAtCurrentLevel; i++) {
                Node node = queue.poll();

                if (node == destination) {
                    found = true;
                    break;
                }

                for (Node neighbor : node.getNeighbors()) {
                    if (!visitedNodes.contains(neighbor)) {
                        visitedNodes.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            if (found) {
                break;
            }
        }
        if (found) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to spend "+String.valueOf(distance-3)+"$ to move?",ButtonType.OK,ButtonType.NO);
            AtomicBoolean respond = new AtomicBoolean(true);
            if (distance>3) {
                alert.showAndWait().ifPresent(response -> {
                    if (response.equals(ButtonType.OK)) {
                        doAction(Actions.MOVE_ACTION);
                        space.removePlayer(this);
                        space = destination;
                        destination.addPlayer(this);
                        respond.set(true);
                    } else {
                        respond.set(false);
                    }
                });
            } else {
                doAction(Actions.MOVE_ACTION);
                space.removePlayer(this);
                space = destination;
                destination.addPlayer(this);
            }
            return respond.get();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"The destination is too far.", ButtonType.CLOSE);
            alert.show();
            return false;
        }
    }

    ////////////////////////////GETTERS AND SETTERS//////////

    public Game getGame() {
        return game;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSanity() {
        return sanity;
    }

    public void setSanity(int sanity) {
        this.sanity = sanity;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public Node getSpace(){return space;}
    public void setSpace(Node node){
        space = node;
    }

    public ArrayList<Integer> getLastTest() {
        return lastTest;
    }

    public void setLastTest(ArrayList<Integer> lastTest) {
        this.lastTest = lastTest;
    }

    public boolean isWithMonsters() {
        return withMonsters;
    }

    public void setWithMonsters(boolean withMonsters) {
        this.withMonsters = withMonsters;
    }
}
