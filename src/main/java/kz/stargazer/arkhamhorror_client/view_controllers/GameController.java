package kz.stargazer.arkhamhorror_client.view_controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.InvestigatorBuilder;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;

public class GameController {


    private Game game;

    public GameController() {
        Game game1 = game.createGame();
        Investigator hero = new InvestigatorBuilder().name("Daniela Reyes").game(game1).health(7)
                .sanity(5).skills(3,3,1,3, 3).focusLimit(3)
                .money(3).build();
        game1.getPlayers().add(hero);
        game1.getBoard().placePlayer("Arkham Advertiser",hero);
    }

}
