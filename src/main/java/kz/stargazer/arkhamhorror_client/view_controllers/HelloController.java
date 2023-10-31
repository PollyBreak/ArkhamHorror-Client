package kz.stargazer.arkhamhorror_client.view_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import kz.stargazer.arkhamhorror_client.Facade.GameFacadeIAzatoth;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.InvestigatorBuilder;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Board;
import kz.stargazer.arkhamhorror_client.brd.BoardBuilder;

import java.io.IOException;

public class HelloController {
    @FXML
    public void onSinglePlayerClick(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/views/game.fxml"));
        GameFacadeIAzatoth gamef = GameFacadeIAzatoth.createGameFacadeIAzatoth();
        gamef.startGame();
        ((Node)actionEvent.getSource()).getScene().setRoot(new BoardFX(gamef.getGame()).build());
    }
}