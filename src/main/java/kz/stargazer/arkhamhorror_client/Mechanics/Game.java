package kz.stargazer.arkhamhorror_client.Mechanics;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

import java.util.ArrayList;

public class Game {
    private ArrayList<Investigator> players = new ArrayList<>();


    public ArrayList<Investigator> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Investigator> players) {
        this.players = players;
    }
}
