package kz.stargazer.arkhamhorror_client.Heroes;

import kz.stargazer.arkhamhorror_client.Assets.*;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Node;

public class InvestigatorBuilder{
    private Game game;
    private String name;
    private Node startNode;
    private int lore;
    private int influence;
    private int observation;
    private int strength;
    private int will;
    private Assets assets = new Assets();
    private Action talent;
    private int health;
    private int sanity;
    private int focusLimit;
    private int money;

    public InvestigatorBuilder name(String name) {
        this.name=name;
        return this;
    }

    public InvestigatorBuilder startSpace(Node node) {
        this.startNode = node;
        return this;
    }

    public InvestigatorBuilder skills(int lore, int influence, int observation, int strength, int will) {
        this.lore = lore;
        this.influence = influence;
        this.observation = observation;
        this.strength = strength;
        this.will = will;
        return this;
    }

    public InvestigatorBuilder asset(AssetCard assetCard) {
        if (assetCard instanceof Item) {
            this.assets.getItems().add((Item) assetCard);
        }
        else if (assetCard instanceof Spell) {
            this.assets.getSpells().add((Spell) assetCard);
        }
        else if (assetCard instanceof Ally) {
            this.assets.getAllies().add((Ally) assetCard);
        }
        return this;
    }

    public InvestigatorBuilder talent(Action talent) {
        this.talent = talent;
        return this;
    }

    public InvestigatorBuilder health(int health) {
        this.health=health;
        return this;
    }

    public InvestigatorBuilder sanity(int sanity) {
        this.sanity=sanity;
        return this;
    }

    public InvestigatorBuilder focusLimit(int limit) {
        this.focusLimit=limit;
        return this;
    }

    public InvestigatorBuilder game(Game game) {
        this.game = game;
        return this;
    }

    public InvestigatorBuilder money(int money){
        this.money=money;
        return this;
    }

    public Investigator build(){
        Investigator newHero = new Investigator(this);
        return newHero;
    }





    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public Node getStartNode() {
        return startNode;
    }

    public int getLore() {
        return lore;
    }

    public int getInfluence() {
        return influence;
    }

    public int getObservation() {
        return observation;
    }

    public int getStrength() {
        return strength;
    }

    public int getWill() {
        return will;
    }

    public Assets getAssets() {
        return assets;
    }

    public Action getTalent() {
        return talent;
    }

    public int getHealth() {
        return health;
    }

    public int getSanity() {
        return sanity;
    }

    public int getFocusLimit() {
        return focusLimit;
    }

    public int getMoney() {
        return money;
    }
}
