package kz.stargazer.arkhamhorror_client.brd;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
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
    public Node fetchNode(String name){
        return this.nodepile.get(name);
    }
    public HashMap<String, Node> getNodepile() {
        return nodepile;
    }

    public void setNodepile(HashMap<String, Node> nodepile) {
        this.nodepile = nodepile;
    }
}
