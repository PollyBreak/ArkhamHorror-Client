package kz.stargazer.arkhamhorror_client.Assets;


import kz.stargazer.arkhamhorror_client.Heroes.Investigator;

public class AssetCard implements Action{
    private int action;
    private Action actionStrategy;

    @Override
    public void use(Investigator investigator) {
        actionStrategy.use(investigator);
    }

}
