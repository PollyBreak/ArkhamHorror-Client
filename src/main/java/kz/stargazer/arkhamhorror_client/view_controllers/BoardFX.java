package kz.stargazer.arkhamhorror_client.view_controllers;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Board;
import kz.stargazer.arkhamhorror_client.brd.Neighborhood;
import kz.stargazer.arkhamhorror_client.brd.Node;
import org.controlsfx.control.cell.ImageGridCell;

public class BoardFX {
    private Game game;
    private Board net;
    private Group elements;
    private final int offsetX = 200;
    private final int offsetY = 200;
    private final String north_path = "/images/tile_northside.png";
    private final String down_path = "/images/tile_downtown.png";
    private final String east_path = "/images/tile_easttown.png";
    private final String merch_path = "/images/tile_merchant.png";
    private final String river_path = "/images/tile_rivertown.png";
    private final String st_lane_path = "/images/tile_lane.png";
    private final String st_park_path = "/images/tile_park.png";
    private final String st_bridge_path = "/images/tile_bridge.png";
    public BoardFX(Game gm){
        game = gm;
        net = gm.getBoard();
    }
    public ScrollPane build(){
        Group northside = createHoodTile(north_path,100,200,net.neighborhoods.get("Northside"));
        Group downtown = createHoodTile(down_path,475,200,net.neighborhoods.get("Downtown"));
        Group easttown = createHoodTile(east_path,850,200,net.neighborhoods.get("Easttown"));
        Group merchant = createHoodTile(merch_path,280,490,net.neighborhoods.get("Merchant District"));
        Group rivertown = createHoodTile(river_path,655,490,net.neighborhoods.get("Rivertown"));
        ImageView st1 = createSingleTile("Street",375,300, net.fetchNode("Street from Northside to Downtown"));
        ImageView st2 = createSingleTile("Street",750,300, net.fetchNode("Street from Downtown to Easttown"));
        ImageView st3 = createSingleTile("Street",275,430, net.fetchNode("Street from Merchant District to Northside"));
        st3.setRotate(135);
        ImageView st4 = createSingleTile("Street",450,430, net.fetchNode("Street from Merchant District to Downtown"));
        st4.setRotate(45);
        ImageView st5 = createSingleTile("Street",650,430, net.fetchNode("Street from Downtown to Rivertown"));
        st5.setRotate(135);
        ImageView st6 = createSingleTile("Street",850,430, net.fetchNode("Street from Rivertown to Easttown"));
        st6.setRotate(45);
        ImageView st7 = createSingleTile("Street",555,580, net.fetchNode("Street from Merchant District to Downtown"));
        Group biggroup = new Group(st1,st2,st3,st4,st5,st6,st7,northside,downtown,easttown,merchant,rivertown);
        elements = biggroup;
        ScrollPane pane = new ScrollPane(biggroup);
        pane.setPrefSize(Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
        pane.setPannable(true);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return pane;
    }
    private ImageView createSingleTile(String imgpath, int x, int y, Node link){
        int w = 300;
        int h = 300;
        if (imgpath.equals("Street")){
            switch (link.getType()){
                case Street_Lane -> imgpath = st_lane_path;
                case Street_Bridge -> imgpath = st_bridge_path;
                case Street_Park -> imgpath = st_park_path;
            }
            w = 125;
            h = 125;
        }
        ImageView img = new ImageView(new Image(getClass().getResource(imgpath).toExternalForm()));
        img.setFitWidth(w);
        img.setFitHeight(h);
        img.setLayoutX(x);
        img.setLayoutY(y);
        img.setUserData(link);
        img.setOnMouseClicked(e->{
            renderPlayer(game.getPlayers().get(0), img);
        });
        return img;
    }
    private Group createHoodTile(String imgpath, int x, int y, Neighborhood hood){
        ImageView img = new ImageView(new Image(getClass().getResource(imgpath).toExternalForm()));
        img.setFitWidth(300);
        img.setFitHeight(300);
        img.setLayoutX(x);
        img.setLayoutY(y);
        Button top = new Button(hood.getNodes().get(0).getName());
        Button mddl = new Button(hood.getNodes().get(1).getName());
        Button lower = new Button(hood.getNodes().get(2).getName());
        top.setOnAction(e->{
            renderPlayer(game.getPlayers().get(0), top);
        });
        top.setLayoutX(img.getLayoutX()+50);
        top.setLayoutY(img.getLayoutY()+30);
        top.setUserData(hood.getNodes().get(0));
        mddl.setOnAction(e->{
            renderPlayer(game.getPlayers().get(0), mddl);
        });
        mddl.setLayoutX(img.getLayoutX()+50);
        mddl.setLayoutY(img.getLayoutY()+150);
        mddl.setUserData(hood.getNodes().get(1));
        lower.setOnAction(e->{
            renderPlayer(game.getPlayers().get(0), lower);
        });
        lower.setLayoutX(img.getLayoutX()+50);
        lower.setLayoutY(img.getLayoutY()+270);
        lower.setUserData(hood.getNodes().get(2));
        Group btngrp = new Group(top,mddl,lower);
        return new Group(img, btngrp);
    }
    private void renderPlayer(Investigator player, javafx.scene.Node anchor) {
        for (javafx.scene.Node node:
             elements.getChildren()) {
            if (node.getUserData() == player){
                elements.getChildren().remove(node);
                break;
            }
        }
        ImageView img = new ImageView(new Image(getClass().getResource("/images/player_daniel.png").toExternalForm()));
        img.setFitHeight(50);
        img.setFitWidth(35);
        int localoffsetX = 20;
        int localoffsetY = -50;
        if (anchor instanceof ImageView){
            localoffsetX= 30;
            localoffsetY= 0;
        }
        img.setLayoutX(anchor.getLayoutX()+localoffsetX);
        img.setLayoutY(anchor.getLayoutY()+localoffsetY);
        img.setUserData(player);
        elements.getChildren().add(img);
    }
}
