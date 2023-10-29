package kz.stargazer.arkhamhorror_client.view_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.InvestigatorBuilder;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Board;
import kz.stargazer.arkhamhorror_client.brd.BoardBuilder;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    Game game;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void onSinglePlayerClick(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/views/game.fxml"));
        Game game1 = game.createGame();
        Investigator hero = new InvestigatorBuilder().name("Daniela Reyes").game(game1).health(7)
                .sanity(5).skills(3,3,1,3, 3).focusLimit(3)
                .money(3).build();
        game1.getPlayers().add(hero);
        game1.getBoard().placePlayer("Arkham Advertiser",hero);
        ((Node)actionEvent.getSource()).getScene().setRoot(new BoardFX(game1).build());
    }
}