package kz.stargazer.arkhamhorror_client.brd;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;

import java.util.ArrayList;
import java.util.List;

public class Node {
    ArrayList<Node> neighbours = new ArrayList<>();
    String name;
    NodeType type;
    int doom;
    private ArrayList<Monster> monsters;
    private ArrayList<Investigator> heroes;

    Node(){};
    Node(String name, NodeType type, Node... neighbours){
        this.name = name;
        this.type= type;
        this.neighbours.addAll(List.of(neighbours));
        this.heroes = new ArrayList<>();
        this.monsters = new ArrayList<>();
    }

    public String getName(){
        return name;
    }
    public void setDoom(int count){
        doom = count;
    }
    public NodeType getType(){
        return type;
    }
    public void link(Node node){
        this.neighbours.add(node);
        node.backlink(this);
    }
    public void backlink(Node node){
        this.neighbours.add(node);
    }


    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<Investigator> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Investigator> heroes) {
        this.heroes = heroes;
    }
    public void addPlayer(Investigator player){
        heroes.add(player);
    }
    public void removePlayer(Investigator player){
        heroes.remove(player);
    }
    public void addMonster(Monster monster){monsters.add(monster);}
    public void removeMonster(Monster monster){monsters.remove(monster);}
    public ArrayList<Node> getNeighbors(){
        return neighbours;
    }
    public boolean addDoom(){
        doom++;
        int overall = doom;
        for (Node node:
             neighbours) {
            if (node.getType()==this.getType()){
                overall+= node.getDoom();
            }
        }
        if (overall==5||doom==3){
            return true;
        }
        return false;
    }
    public void removeDoom(){
        if(doom>0) doom--;
    }
    public int getDoom() {
        return doom;
    }
}
