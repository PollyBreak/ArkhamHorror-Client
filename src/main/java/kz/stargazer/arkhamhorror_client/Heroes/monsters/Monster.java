package kz.stargazer.arkhamhorror_client.Heroes.monsters;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import kz.stargazer.arkhamhorror_client.Assets.Actions;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.Mechanics.Subscriber;
import kz.stargazer.arkhamhorror_client.brd.Node;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Monster implements Subscriber {
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

    public void move(Node targetNode) {
        List<Node> path = findPathWithinDistance(space, targetNode, 2);
        if (path != null && !path.isEmpty()) {
            space.removeMonster(this);
            space = path.get(Math.min(2, path.size() - 1));
            path.get(Math.min(2, path.size() - 1)).addMonster(this);
            game.getFX().renderMonster(this,this.space);
        } else {
            System.out.println("No valid path within 2 nodes to the target.");
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

            if (current == target) {
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
