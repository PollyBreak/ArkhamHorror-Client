package kz.stargazer.arkhamhorror_client.Facade;

import kz.stargazer.arkhamhorror_client.Assets.AssetCard;
import kz.stargazer.arkhamhorror_client.Assets.Item;
import kz.stargazer.arkhamhorror_client.Assets.actionStrategies.ShotgunStrategy;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.InvestigatorBuilder;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.factory.MonsterFactory;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.factory.MonsterType;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.Mechanics.MonsterPhaseLogic;
import kz.stargazer.arkhamhorror_client.Mechanics.Phases;
import kz.stargazer.arkhamhorror_client.brd.BoardBuilder;
import kz.stargazer.arkhamhorror_client.brd.Node;

import java.util.ArrayList;

public class GameFacadeIAzatoth implements GameFacadeInterface{
    private static GameFacadeIAzatoth gameFacadeIAzatothInstance;
    private Game game;
    private MonsterFactory monsterFactory= new MonsterFactory();
    @Override
    public void startGame() {
        game = new Game();
        BoardBuilder boarder = new BoardBuilder();
        game.setBoard(boarder.build("Azatoth"));
        game.setPlayers(new ArrayList<>());
        game.setCurrentPhase(Phases.ACTION_PHASE);
        MonsterPhaseLogic monsterPhaseLogic = new MonsterPhaseLogic();
        game.setMonsterPhaseLogic(monsterPhaseLogic);
        Investigator mainHero = new InvestigatorBuilder().name("Daniela Reyes").game(game).health(7)
                .sanity(5).skills(3,3,1,3, 3).focusLimit(3)
                .money(3).startSpace(game.getBoard().fetchNode("Arkham Advertiser")).build();

            //initial assets
            Item shortgun = new Item(new ShotgunStrategy(3));
            mainHero.getAssets().getItems().add(shortgun);

        game.getPlayers().add(mainHero);
        game.getBoard().placePlayer(mainHero.getSpace().getName(),mainHero);

        monsterFactory.createMonster(MonsterType.PATROL,game, "Robed Figure","Independence Square",
                1, 0, 1);
        monsterFactory.createMonster(MonsterType.PATROL,game, "Robed Figure","Black Cave",
                1, 0, 1);
        monsterFactory.createMonster(MonsterType.LURKER, game, "High Priest","Independence Square",
                2, 1, 2);
        monsterFactory.createMonster(MonsterType.HUNTER, game, "Swift Byakhee","Independence Square",
                2, 2, 3);

        game.subscribeMonsters(game.getMonsters().toArray(new Monster[0]));
        game.setUnstableSpace(game.getBoard().fetchNode("Arkham Advertiser"));
        game.notifyMonsters();
    }
    public Game getGame(){
        return game;
    }
    private GameFacadeIAzatoth() {
    }

    public static GameFacadeIAzatoth createGameFacadeIAzatoth(){
        if (gameFacadeIAzatothInstance == null){
            gameFacadeIAzatothInstance = new GameFacadeIAzatoth();
        }
        return gameFacadeIAzatothInstance;
    }

}
