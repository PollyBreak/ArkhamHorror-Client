package kz.stargazer.arkhamhorror_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kz.stargazer.arkhamhorror_client.Assets.Action;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.InvestigatorBuilder;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;

import java.io.IOException;

public class ArkhamHorrorClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ArkhamHorrorClientApplication.class.getResource("/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Arkham Horror!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        Game game = Game.createGame();
        Investigator hero = new InvestigatorBuilder().name("Daniela Reyes").game(game).health(7)
                .sanity(5).skills(3,3,1,3, 3).focusLimit(3)
                .money(3).build();
        
    }

    public static void main(String[] args) {
        launch();
    }

}