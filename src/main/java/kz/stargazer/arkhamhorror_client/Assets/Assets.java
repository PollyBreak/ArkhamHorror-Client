package kz.stargazer.arkhamhorror_client.Assets;

import java.util.ArrayList;
import java.util.List;

public class Assets {
    private List<Item> items = new ArrayList<>();
    private List<Spell> spells = new ArrayList<>();
    private List<Ally> allies = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public List<Ally> getAllies() {
        return allies;
    }

    public void setAllies(List<Ally> allies) {
        this.allies = allies;
    }
}
