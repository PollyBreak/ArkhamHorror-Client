package kz.stargazer.arkhamhorror_client.Assets;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

import java.util.ArrayList;

public class Spell extends AssetCard{
    private int value;
    private int hands;
    private ArrayList<Integer> traits;
    private int sanityDecrease;

    public Spell(Action actionStrategy) {
        super(actionStrategy);
    }
}
