package kz.stargazer.arkhamhorror_client.Facade;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.InvestigatorBuilder;
import kz.stargazer.arkhamhorror_client.Heroes.Monster;
import kz.stargazer.arkhamhorror_client.Heroes.Patrol;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.Mechanics.MonsterPhaseLogic;
import kz.stargazer.arkhamhorror_client.Mechanics.Phases;
import kz.stargazer.arkhamhorror_client.brd.BoardBuilder;
import kz.stargazer.arkhamhorror_client.brd.Node;

import java.util.ArrayList;

public class GameFacadeIAzatoth implements GameFacadeInterface{
    private static GameFacadeIAzatoth gameFacadeIAzatothInstance;
    private Game game;
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
        game.getPlayers().add(mainHero);
        game.getBoard().placePlayer(mainHero.getSpace().getName(),mainHero);
        Node monster1spawn = game.getBoard().fetchNode("Independence Square");
        Monster monster1 = new Patrol(game,"Robbed Figure",
                "Independence Square", monster1spawn, 1, 0, 1);
        Node monster2spawn = game.getBoard().fetchNode("Black Cave");
        Monster monster2 = new Patrol(game, "Robbed Figure",
                "Black Cave", monster2spawn, 1, 0, 1);
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
