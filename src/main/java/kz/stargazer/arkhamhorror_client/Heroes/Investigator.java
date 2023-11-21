package kz.stargazer.arkhamhorror_client.Heroes;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import kz.stargazer.arkhamhorror_client.Assets.Action;
import kz.stargazer.arkhamhorror_client.Assets.Actions;
import kz.stargazer.arkhamhorror_client.Assets.Assets;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;
import kz.stargazer.arkhamhorror_client.Mechanics.StatePattern.ActionResult;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.Mechanics.Phases;
import kz.stargazer.arkhamhorror_client.Mechanics.StatePattern.AttackResult;
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
    private int TEMP_true_successes;

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
        game.getFX().updateStats();
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
    public boolean moveWithMoney(int requested, Node destination) {
        String alertmsg = "Do you want to spend " + String.valueOf(requested) + "$ to move? (You have " + String.valueOf(money) + ")";
        if (requested<1) {
            requested = 0;
            alertmsg = "There is a monster ahead! Prepare to fight!";
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertmsg, ButtonType.OK, ButtonType.NO);
        AtomicBoolean respond = new AtomicBoolean(true);
        int finalRequested = requested;
        alert.showAndWait().ifPresent(response -> {
                        if (response.equals(ButtonType.OK)) {
                            if (money>= finalRequested) {
                                if (destination==null){
                                    respond.set(true);
                                } else {
                                    money -= finalRequested;
                                    space.removePlayer(this);
                                    space = destination;
                                    destination.addPlayer(this);
                                    respond.set(true);
                                }
                            } else {
                                Alert alert_reject = new Alert(Alert.AlertType.ERROR,"You do not have enough cash.", ButtonType.CLOSE);
                                alert_reject.show();
                                respond.set(false);
                            }
                        } else {
                            respond.set(false);
                        }
                    });
        return respond.get();
    }
    public Node move(Node destination){
//        if (doneActions.contains(Actions.MOVE_ACTION)) {
//            return false;
//        } else {
        if (withMonsters) {
            return null;
        } else {
            int distance = 0;
            boolean found = false;
            List<Node> path = findPathWithinDistance(space, destination, 4);
            if (path != null && !path.isEmpty()) {
                found = true;
                distance = path.size()-1;
            }
            if (found && distance < 3) {
                for (Node node :
                        path) {
                    space.removePlayer(this);
                    space = node;
                    node.addPlayer(this);
                    checkIfMonster();
                    if (this.withMonsters) {
                        for (Monster monster:
                                node.getMonsters()) {
                            game.getFX().placeMonsterToHand(monster);
                            monster.setEngaged(true);
                            monster.setGoal(this);
                        }
                        doAction(Actions.MOVE_ACTION);
                        return node;
                    }
                }
                doAction(Actions.MOVE_ACTION);
                return destination;
            } else
            if (found && distance<5) {
                int livedistance = -1;
                boolean result = false;
                for (Node node:
                    path) {
                    result = false;
                    livedistance++;
                    if (!node.getMonsters().isEmpty()){
                        result = moveWithMoney(livedistance-2,null);
                        if (result) {
                            space.removePlayer(this);
                            space = node;
                            node.addPlayer(this);
                            withMonsters = true;
                            for (Monster monster:
                                 node.getMonsters()) {
                                game.getFX().placeMonsterToHand(monster);
                                monster.setEngaged(true);
                                monster.setGoal(this);
                            }
                            doAction(Actions.MOVE_ACTION);
                            return node;
                        }
                        else withMonsters = false;
                        return null;
                    }
                }
                result = moveWithMoney(livedistance-2,destination);
                if (result) {
                    doAction(Actions.MOVE_ACTION);
                    return destination;
                }
                return null;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The destination is too far.", ButtonType.CLOSE);
                alert.show();
                return null;
            }
        }
    }

    private List<Node> findPathWithinDistance(Node start, Node target, int maxDistance) {
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(target)) {
                return buildPath(parentMap, target);
            }
            for (Node neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return null;
    }

    private List<Node> buildPath(Map<Node, Node> parentMap, Node target) {
        List<Node> path = new ArrayList<>();
        Node current = target;
        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public boolean ward() {
        if (space.getDoom()==0 || doneActions.contains(Actions.WARD_ACTION)) {
            return false;
        } else {
            game.setCurrent_action(Actions.WARD_ACTION);
            test(lore);
            actionResult = new WardResult(this, null);
            actionResult.act();
            //
            // remove anomaly on full clear
            if (game.checkAnomaly(space.getType())) {
                int doomsum = space.getDoom();
                for (Node neighb :
                        space.getNeighbors()) {
                    if (neighb.getType() == space.getType()) {
                        doomsum += neighb.getDoom();
                    }
                }
                if (doomsum==0){
                    game.endAnomaly(space.getType());
                }
            }
            //
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
                if (!monster.isEngaged()&&!monster.isExhausted()){
                    withMonsters = true;
                    break;
                }
            }
        }
    }

    public void hit(Monster monster) {
        game.setCurrent_action(Actions.ATTACK_ACTION);
        actionResult = new AttackResult(this, null, monster);
        actionResult.act();
        test(strength);
        doAction(Actions.ATTACK_ACTION);
        checkIfMonster();
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
    public void setTEMP_true_successes(int TEMP_true_successes) {
        this.TEMP_true_successes = TEMP_true_successes;
    }
    public int getTEMP_true_successes() {
        return TEMP_true_successes;
    }
    public void addGluedMonster(Monster monster){
        this.gluedMonsters.add(monster);
    }
    public void removeGluedMonster(Monster monster){
        this.gluedMonsters.remove(monster);
    }

    public ArrayList<Monster> getGluedMonsters() {
        return gluedMonsters;
    }

    public void setGluedMonsters(ArrayList<Monster> gluedMonsters) {
        this.gluedMonsters = gluedMonsters;
    }
}
