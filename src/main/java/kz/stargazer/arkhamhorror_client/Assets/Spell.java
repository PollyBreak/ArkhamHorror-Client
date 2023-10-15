package kz.stargazer.arkhamhorror_client.Assets;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

import java.util.ArrayList;

public class Spell extends AssetCard{
    private int value;
    private int hands;
    private ArrayList<Integer> traits;
    private int sanityDecrease;
    private Action actionStrategy;

    @Override
    public void use(Investigator investigator) {
        actionStrategy.use(investigator);
    }
}
