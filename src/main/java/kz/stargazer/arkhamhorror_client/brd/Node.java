package com.example.archam;

import java.util.ArrayList;
import java.util.List;

public class Node {
    ArrayList<Node> neighbours = new ArrayList<>();
    String name;
    NodeType type;
    int doom;
    boolean MONSTER_PLACEHOLDER = false; //monster class is to be added
    boolean PLAYER_REF = false; //players have to be there as well?
    Node(){};
    Node(String name, NodeType type, Node... neighbours){
        this.name = name;
        this.type= type;
        this.neighbours.addAll(List.of(neighbours));
    }
    public void setDoom(int count){
        doom = count;
    }
    public void link(Node node){
        this.neighbours.add(node);
        node.backlink(this);
    }
    public void backlink(Node node){
        this.neighbours.add(node);
    }
}
