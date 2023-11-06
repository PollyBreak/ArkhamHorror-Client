package kz.stargazer.arkhamhorror_client.Mechanics;

import kz.stargazer.arkhamhorror_client.Assets.Actions;
import kz.stargazer.arkhamhorror_client.Assets.Ally;
import kz.stargazer.arkhamhorror_client.Assets.Item;
import kz.stargazer.arkhamhorror_client.Assets.Spell;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;
import kz.stargazer.arkhamhorror_client.brd.Board;
import kz.stargazer.arkhamhorror_client.brd.Node;
import kz.stargazer.arkhamhorror_client.view_controllers.BoardFX;

import java.util.ArrayList;
import java.util.Arrays;

public class Game implements Publisher {
    private Plot plot;
    private Board board;
    private BoardFX fx;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<Subscriber> monstersSubscribers = new ArrayList<>();
    private ArrayList<Investigator> players;
    private Node unstableSpace;
    private ArrayList<Item> items;
    private ArrayList<Spell> spells;
    private ArrayList<Ally> allies;
    private ArrayList<Item> shop;
    private Actions current_action;
    private MonsterPhaseLogic monsterPhaseLogic;
    private Phases currentPhase;

    public void addDoom(Node node){
        board.fetchNode(node.getName()).addDoom();
        fx.renderDoom(node);
    }

    @Override
    public void addMonster(Subscriber subscriber) {
        monstersSubscribers.add(subscriber);
    }

    @Override
    public void removeMonster(Subscriber subscriber) {
        monstersSubscribers.remove(subscriber);
    }

    @Override
    public void notifyMonsters() {
        for(Subscriber monster : monstersSubscribers){
            monster.update(this);
        }
    }

    public void runMonsterPhase(){
        currentPhase = Phases.ACTION_PHASE;
        monsterPhaseLogic.runPhase(this);

        ////THIS SECTION IS FOR TESTING PURPOSES ONLY
        ///REMOVE AFTER FULL FUNCTIONALITY IMPLEMENTED
        setUnstableSpace(this.getBoard().getNodepile().values().toArray(new Node[0])[(int)(Math.random()*this.getBoard().getNodepile().values().size())]);
        notifyMonsters();
        ////
    }

    public void finish() {

    }


    public ArrayList<Investigator> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Investigator> players) {
        this.players = players;
    }
    public void subscribeMonsters(Monster ... monsters){
        monstersSubscribers.addAll(Arrays.asList(monsters));
    }
    public Game(){};





  ////////////////////// getters and setters/////////////////////////////////

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

    public Actions getCurrent_action() {
        return current_action;
    }

    public void setCurrent_action(Actions current_action) {
        this.current_action = current_action;
    }

    public Phases getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Phases currentPhase) {
        this.currentPhase = currentPhase;
    }
    public void setFX (BoardFX fx){
      this.fx = fx;
    }
    public BoardFX getFX(){return fx;}
    public MonsterPhaseLogic getMonsterPhaseLogic() {
        return monsterPhaseLogic;
    }

    public void setMonsterPhaseLogic(MonsterPhaseLogic monsterPhaseLogic) {
        this.monsterPhaseLogic = monsterPhaseLogic;
    }
}
