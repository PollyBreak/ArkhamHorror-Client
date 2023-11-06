package kz.stargazer.arkhamhorror_client.Mechanics;

public interface Publisher {

    public void addMonster(Subscriber subscriber);
    public void removeMonster(Subscriber subscriber);
    public void notifyMonsters();

}
