package kz.stargazer.arkhamhorror_client.Mechanics;

import kz.stargazer.arkhamhorror_client.Heroes.Monster;

import java.util.ArrayList;

public interface Publisher {

    public void addMonster(Subscriber subscriber);
    public void removeMonster(Subscriber subscriber);
    public void notifyMonsters();

}
