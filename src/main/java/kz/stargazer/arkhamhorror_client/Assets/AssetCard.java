package kz.stargazer.arkhamhorror_client.Assets;


import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

public abstract class AssetCard {
    private int action;
    abstract void use(Investigator hero);
}
