package kz.stargazer.arkhamhorror_client.Facade;

import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.InvestigatorBuilder;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.Mechanics.MonsterPhaseLogic;
import kz.stargazer.arkhamhorror_client.Mechanics.Phases;
import kz.stargazer.arkhamhorror_client.brd.BoardBuilder;

import java.util.ArrayList;

public class GameFacadeIAzatoth implements GameFacadeInterface{
    @Override
    public void startGame() {
        Game game = Game.createGame();
        BoardBuilder boarder = new BoardBuilder();
        game.setBoard(boarder.build("Azatoth"));
        game.setPlayers(new ArrayList<>());
        game.setCurrentPhase(Phases.ACTION_PHASE);
        MonsterPhaseLogic monsterPhaseLogic = new MonsterPhaseLogic();
        game.setMonsterPhaseLogic(monsterPhaseLogic);
        Investigator mainHero = new InvestigatorBuilder().name("Daniela Reyes").game(game).health(7)
                .sanity(5).skills(3,3,1,3, 3).focusLimit(3)
                .money(3).build();
        game.getPlayers().add(mainHero);


    }

    GameFacadeIAzatoth() {
    }
}
