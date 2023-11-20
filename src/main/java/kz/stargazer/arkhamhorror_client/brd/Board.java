package kz.stargazer.arkhamhorror_client.brd;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;

import java.util.HashMap;

public class Board {
    private String scenario;
    private HashMap<String,Node> nodepile = new HashMap<>();
    private HashMap<String,Node> playerpos = new HashMap<>(); //players position
    private HashMap<String,Node> monsterpos = new HashMap<>(); //monsters position
    public HashMap<String,Neighborhood> neighborhoods = new HashMap<>();
    public Board(){};
    public void connectHoods(String first, String second, int position, NodeType streetType){
        this.neighborhoods.get(first).connect(this.neighborhoods.get(second),position,new Node("Street from "+neighborhoods.get(first).name+" to "+neighborhoods.get(second).name,streetType));
    }

    public void placePlayer(String spacename, Investigator player){
        this.nodepile.get(spacename).addPlayer(player);
        player.setSpace(this.nodepile.get(spacename));
    }
    public void placeMonster(String spacename, Monster monster){
        this.nodepile.get(spacename).addMonster(monster);
        monster.setSpace(this.nodepile.get(spacename));
    }
    public Neighborhood getHoodOfNode(NodeType type){
        switch (type){
            case Child_Uptown -> {return neighborhoods.get("Uptown");}
            case Child_Downtown -> {return neighborhoods.get("Downtown");}
            case Child_Easttown -> {return neighborhoods.get("Easttown");}
            case Child_Merchant -> {return neighborhoods.get("Merchant District");}
            case Child_Northside -> {return neighborhoods.get("Northside");}
            case Child_Rivertown -> {return neighborhoods.get("Rivertown");}
            default -> {return null;}
        }
    }
    public Node fetchNode(String name){
        return this.nodepile.get(name);
    }
    public HashMap<String, Node> getNodepile() {
        return nodepile;
    }
    public void setNodepile(HashMap<String, Node> nodepile) {
        this.nodepile = nodepile;
    }
    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getScenario() {
        return scenario;
    }
}
