package kz.stargazer.arkhamhorror_client.Heroes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import kz.stargazer.arkhamhorror_client.Assets.Action;
import kz.stargazer.arkhamhorror_client.Assets.Actions;
import kz.stargazer.arkhamhorror_client.Assets.Assets;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;
import kz.stargazer.arkhamhorror_client.Mechanics.StatePattern.ActionResult;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.Mechanics.Phases;
import kz.stargazer.arkhamhorror_client.Mechanics.StatePattern.EvadeResult;
import kz.stargazer.arkhamhorror_client.Mechanics.StatePattern.WardResult;
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

    private ArrayList<Monster> gluedMonsters = new ArrayList<>();

    private Action talent;

    private boolean delayed;
    private boolean withMonsters;
    private boolean active;
    private boolean ready;
    private boolean alive;
    private ActionResult actionResult;

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
        if (actions == 0 || ready) {
            finishTurn();
        }
    }

    private void finishTurn() {
        doneActions = new ArrayList<Actions>();
        game.runMonsterPhase();
        actions = 2;
        ready = false;
    }


    public ArrayList<Integer> test(int skill){
        lastTest.clear();
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
        if (!isAlive()){
            game.finish();
        }
    }

    public boolean move(Node destination) {
//        if (doneActions.contains(Actions.MOVE_ACTION)) {
//            return false;
//        } else {
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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to spend "+String.valueOf(distance-3)+"$ to move? (You have "+String.valueOf(money)+")",ButtonType.OK,ButtonType.NO);
                AtomicBoolean respond = new AtomicBoolean(true);
                if (distance>3) {
                    int finalDistance = distance;
                    alert.showAndWait().ifPresent(response -> {
                        if (response.equals(ButtonType.OK)) {
                            if (money>=finalDistance-3) {
                                money-=finalDistance-3;
                                space.removePlayer(this);
                                space = destination;
                                destination.addPlayer(this);
                                checkIfMonster();
                                respond.set(true);
                                doAction(Actions.MOVE_ACTION);
                            } else {
                                Alert alert_reject = new Alert(Alert.AlertType.ERROR,"You do not have enough cash.", ButtonType.CLOSE);
                                alert_reject.show();
                                respond.set(false);
                            }
                        } else {
                            respond.set(false);
                        }
                    });
                } else {
                    space.removePlayer(this);
                    space = destination;
                    checkIfMonster();
                    destination.addPlayer(this);
                    doAction(Actions.MOVE_ACTION);
                }
                return respond.get();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"The destination is too far.", ButtonType.CLOSE);
                alert.show();
                return false;
            }
//        }
    }

    public boolean ward() {
        if (space.getDoom()==0 || doneActions.contains(Actions.WARD_ACTION)) {
            return false;
        } else {
            game.setCurrent_action(Actions.WARD_ACTION);
            test(lore);
            actionResult = new WardResult(this, null);
            doAction(Actions.WARD_ACTION);
            return true;
        }
    }

    public void gatherMoney(){
        if (!doneActions.contains(Actions.GATHER_RESOURCES_ACTION)){
            money++;
            doAction(Actions.GATHER_RESOURCES_ACTION);
        }
    }

    public void evade(Monster monster) {
        test(observation);
        actionResult = new EvadeResult(this, null, monster);
    }

    public void finishTest() {
        actionResult.act();
        if (game.getCurrentPhase() == Phases.ACTION_PHASE) {
            doAction(game.getCurrent_action());
        }
    }

    public int countSuccesses() {
        int count = 0;
        for (int dice: lastTest) {
            if (successes.contains(dice)){
                count++;
            }
        }
        return count;
    }

    public void checkIfMonster() {
        if (space.getMonsters().isEmpty()) {
            withMonsters = false;
        }
        else {
            for (Monster monster:space.getMonsters()){
                if (!(monster.isEngaged())&&!(monster.isExhausted())){
                    withMonsters = true;
                }
            }
        }
    }

    public void hit(Monster monster) {

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

    public int getClues() {
        return clues;
    }

    public void setClues(int clues) {
        this.clues = clues;
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }
    public String getName(){
        return name;
    }
    public int getMoney() {
        return money;
    }
}
