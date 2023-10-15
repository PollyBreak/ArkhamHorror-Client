package kz.stargazer.arkhamhorror_client.brd;

import java.util.ArrayList;

public class Neighborhood {
    ArrayList<Node> spaces = new ArrayList<>();
    String name;
    boolean anomaly = false;
    boolean leftOrientation = false; //topmost space may be on the right and on the left of a tile
    int clues;
    Neighborhood(String name){
        this.name = name;
    }
    Neighborhood(String name, boolean leftorientation){
        this.name = name;
        leftOrientation = leftorientation;
    }
    public void addNode(Node node){
        spaces.add(node);
    }
    public void deploy(){
        if (spaces.size()==3){
            spaces.get(0).link(spaces.get(1));
            spaces.get(0).link(spaces.get(2));
            spaces.get(1).link(spaces.get(2));
        }
    }
    public void connect(Neighborhood hood, int pos, Node street){
        // position is responsible for connection socket on a tile
        // spaces are counted counterclockwise starting from the topmost
        // positions counter together with spaces stating with single topmost socket
        int thispos = pos;
        int outerpos = pos;
        if (!this.leftOrientation && hood.leftOrientation){
            outerpos = (outerpos-1);
            if (outerpos==0) outerpos=6;
        }
        if (this.leftOrientation && !hood.leftOrientation){
            outerpos = (outerpos+1);
            if (outerpos==7) outerpos=1;
        }
        outerpos = (outerpos+3)%6;
        if (outerpos==0) outerpos=6;
        if (thispos%2!=0){
            int thisspace = thispos/2;
            spaces.get(thisspace).link(street);
        } else {
            int thisspace1 = ((thispos-1)/2)%3;
            int thisspace2 = (thispos/2)%3;
            spaces.get(thisspace1).link(street);
            spaces.get(thisspace2).link(street);
        }
        if (outerpos%2==0) {
            int outerspace1 = (((outerpos) - 1) / 2) % 3;
            int outerspace2 = ((outerpos) / 2) % 3;
            hood.spaces.get(outerspace1).link(street);
            hood.spaces.get(outerspace2).link(street);
        } else {
            int outerspace = outerpos/2;
            hood.spaces.get(outerspace).link(street);
        }
    }
    public ArrayList<Node> getNodes(){
        return spaces;
    }
}
