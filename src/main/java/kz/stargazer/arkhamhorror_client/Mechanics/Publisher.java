package kz.stargazer.arkhamhorror_client.Mechanics;

import kz.stargazer.arkhamhorror_client.Heroes.Monster;

import java.util.ArrayList;

public interface Publisher {

    public void addSubscriber(Subscriber subscriber);
    public void removeMonsterSubscriber(Subscriber subscriber);
    public void notifySubscribers();

}
