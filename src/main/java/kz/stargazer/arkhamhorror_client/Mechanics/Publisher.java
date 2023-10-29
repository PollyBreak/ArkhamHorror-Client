package kz.stargazer.arkhamhorror_client.Mechanics;

import kz.stargazer.arkhamhorror_client.Heroes.Monster;

import java.util.ArrayList;

public class Publisher {
    ArrayList<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }
    public void removeMonsterSubscriber(Subscriber subscriber){
        subscribers.remove(subscriber);
    }
    public void notifySubscribers(){
        for (Subscriber s:subscribers) {
//            s.update();
        }
    }
}
