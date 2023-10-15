package com.example.archam;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    HashMap<String,Node> nodepile = new HashMap<>();
    HashMap<String,Node> playerpos = new HashMap<>(); //players position
    HashMap<String,Node> monsterpos = new HashMap<>(); //monsters position
    HashMap<String,Neighborhood> neighborhoods = new HashMap<>();
    Board(){};
    public void connectHoods(String first, String second, int position, NodeType streetType){
        this.neighborhoods.get(first).connect(this.neighborhoods.get(second),position,new Node("Street",streetType));
    }
}
