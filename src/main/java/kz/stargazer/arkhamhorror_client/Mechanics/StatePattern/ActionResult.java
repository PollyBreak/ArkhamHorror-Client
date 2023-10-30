package kz.stargazer.arkhamhorror_client.Mechanics.StatePattern;

import kz.stargazer.arkhamhorror_client.Assets.Actions;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;

public abstract class ActionResult {
    protected Investigator player;
    protected ActionResult nextResult;

    public abstract void act();

    public ActionResult(Investigator player, ActionResult nextResult) {
        this.player = player;
        this.nextResult = nextResult;
    }
}
