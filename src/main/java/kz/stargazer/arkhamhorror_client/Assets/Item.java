package kz.stargazer.arkhamhorror_client.Assets;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

import java.util.ArrayList;

public class Item extends AssetCard{
    private int value;
    private int hands;
    private int health;
    private int sanity;
    private ArrayList<Integer> traits;

    private Action actionStrategy;
    @Override
    public void use(Investigator investigator) {
        actionStrategy.use(investigator);
    }
}
