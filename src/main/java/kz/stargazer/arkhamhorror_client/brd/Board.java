package kz.stargazer.arkhamhorror_client.brd;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private HashMap<String,Node> nodepile = new HashMap<>();
    private HashMap<String,Node> playerpos = new HashMap<>(); //players position
    private HashMap<String,Node> monsterpos = new HashMap<>(); //monsters position
    public HashMap<String,Neighborhood> neighborhoods = new HashMap<>();
    Board(){};
    public void connectHoods(String first, String second, int position, NodeType streetType){
        this.neighborhoods.get(first).connect(this.neighborhoods.get(second),position,new Node("Street",streetType));
    }


    public HashMap<String, Node> getNodepile() {
        return nodepile;
    }

    public void setNodepile(HashMap<String, Node> nodepile) {
        this.nodepile = nodepile;
    }
}
