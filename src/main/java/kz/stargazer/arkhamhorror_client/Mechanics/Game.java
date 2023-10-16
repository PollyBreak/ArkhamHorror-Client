package kz.stargazer.arkhamhorror_client.Mechanics;

import kz.stargazer.arkhamhorror_client.Assets.Ally;
import kz.stargazer.arkhamhorror_client.Assets.Item;
import kz.stargazer.arkhamhorror_client.Assets.Spell;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.Monster;
import kz.stargazer.arkhamhorror_client.brd.Board;
import kz.stargazer.arkhamhorror_client.brd.Node;

import java.util.ArrayList;

public class Game {
    private Plot plot;
    private Board board;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<Investigator> players = new ArrayList<>();
    private Node unstableSpace;
    private ArrayList<Item> items;
    private ArrayList<Spell> spells;
    private ArrayList<Ally> allies;
    private ArrayList<Item> shop;
    private int current_action;

    public void play(){}
    public ArrayList<Investigator> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Investigator> players) {
        this.players = players;
    }





    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public Node getUnstableSpace() {
        return unstableSpace;
    }

    public void setUnstableSpace(Node unstableSpace) {
        this.unstableSpace = unstableSpace;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spell> spells) {
        this.spells = spells;
    }

    public ArrayList<Ally> getAllies() {
        return allies;
    }

    public void setAllies(ArrayList<Ally> allies) {
        this.allies = allies;
    }

    public ArrayList<Item> getShop() {
        return shop;
    }

    public void setShop(ArrayList<Item> shop) {
        this.shop = shop;
    }

    public int getCurrent_action() {
        return current_action;
    }

    public void setCurrent_action(int current_action) {
        this.current_action = current_action;
    }
}
