package com.example.archam;

import java.util.ArrayList;
import java.util.HashSet;

public class BoardBuilder {
    Board board;
    BoardBuilder(){board = new Board();};
    public void buildNeighborhood_Rivertown(){
        Neighborhood rivertown = new Neighborhood("Rivertown", true);
        rivertown.addNode(new Node("Black Cave",NodeType.Child_Rivertown));
        rivertown.addNode(new Node("General Store",NodeType.Child_Rivertown));
        rivertown.addNode(new Node("Graveyard",NodeType.Child_Rivertown));
        rivertown.deploy();
        board.neighborhoods.put(rivertown.name,rivertown);
    }
    public void buildNeighborhood_Merchant(){
        Neighborhood merchant = new Neighborhood("Merchant District");
        merchant.addNode(new Node("Unvisited Island",NodeType.Child_Merchant));
        merchant.addNode(new Node("River Docks",NodeType.Child_Merchant));
        merchant.addNode(new Node("Tick-Tock Club",NodeType.Child_Merchant));
        merchant.deploy();
        board.neighborhoods.put(merchant.name,merchant);
    }
    public void buildNeighborhood_Northside(){
        Neighborhood northside = new Neighborhood("Northside");
        northside.addNode(new Node("Arkham Advertiser",NodeType.Child_Northside));
        northside.addNode(new Node("Curiositie Shoppe",NodeType.Child_Northside));
        northside.addNode(new Node("Train Station",NodeType.Child_Northside));
        northside.deploy();
        board.neighborhoods.put(northside.name,northside);
    }
    public void buildNeighborhood_Downtown(){
        Neighborhood downtown = new Neighborhood("Downtown");
        downtown.addNode(new Node("Independance Square",NodeType.Child_Downtown));
        downtown.addNode(new Node("Arkham Asylum",NodeType.Child_Downtown));
        downtown.addNode(new Node("La Bella Luna",NodeType.Child_Downtown));
        downtown.deploy();
        board.neighborhoods.put(downtown.name,downtown);
    }
    public void buildNeighborhood_Easttown(){
        Neighborhood easttown = new Neighborhood("Easttown",true);
        easttown.addNode(new Node("Velma's Dinner",NodeType.Child_Easttown));
        easttown.addNode(new Node("Police Station",NodeType.Child_Easttown));
        easttown.addNode(new Node("Hibb's Roadhouse",NodeType.Child_Easttown));
        easttown.deploy();
        board.neighborhoods.put(easttown.name, easttown);
    }
    public Board buildScenario_Azatoth(){
        buildNeighborhood_Rivertown();
        buildNeighborhood_Merchant();
        buildNeighborhood_Downtown();
        buildNeighborhood_Easttown();
        buildNeighborhood_Northside();
        board.connectHoods("Merchant District","Northside",2,NodeType.Street_Park);
        board.connectHoods("Merchant District", "Downtown", 1, NodeType.Street_Lane);
        board.connectHoods("Merchant District", "Rivertown", 6,NodeType.Street_Lane);
        board.connectHoods("Northside", "Downtown", 6, NodeType.Street_Park);
        board.connectHoods("Downtown","Easttown", 6, NodeType.Street_Lane);
        board.connectHoods("Downtown","Rivertown",5,NodeType.Street_Bridge);
        board.connectHoods("Rivertown","Easttown",6,NodeType.Street_Bridge);
        fillNodePile(board.neighborhoods.values().stream().findFirst().get().spaces.get(0),new HashSet<>());
        return board;
    }
    public Board build(String scenario){
        if (scenario.equals("Azatoth")){
            return buildScenario_Azatoth();
        }
        return board;
    }
    private void fillNodePile(Node root, HashSet<Node> visited){
        visited.add(root);
        board.nodepile.put(root.name,root);
        for (Node node:
             root.neighbours) {
            if (!visited.contains(node)){
                fillNodePile(node,visited);
            }
        }
    }
}
