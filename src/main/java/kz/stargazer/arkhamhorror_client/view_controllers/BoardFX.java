package kz.stargazer.arkhamhorror_client.view_controllers;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Board;
import kz.stargazer.arkhamhorror_client.brd.Neighborhood;
import kz.stargazer.arkhamhorror_client.brd.Node;

import java.util.HashMap;

public class BoardFX {
    private Game game;
    private Board net;
    private Group elements = new Group();
    private HashMap<javafx.scene.Node,HBox> statusboxes = new HashMap<>();
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
        gm.setFX(this);
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
        st3.setRotate(45);
        ImageView st4 = createSingleTile("Street",450,430, net.fetchNode("Street from Merchant District to Downtown"));
        st4.setRotate(135);
        ImageView st5 = createSingleTile("Street",650,430, net.fetchNode("Street from Downtown to Rivertown"));
        st5.setRotate(45);
        ImageView st6 = createSingleTile("Street",850,430, net.fetchNode("Street from Rivertown to Easttown"));
        st6.setRotate(135);
        ImageView st7 = createSingleTile("Street",555,580, net.fetchNode("Street from Merchant District to Rivertown"));
        Group biggroup = new Group(st1,st2,st3,st4,st5,st6,st7,northside,downtown,easttown,merchant,rivertown);
        elements.getChildren().add(biggroup);
        ScrollPane pane = new ScrollPane(elements);
        pane.setPrefSize(Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
        pane.setPannable(true);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for (javafx.scene.Node node:
             statusboxes.values()) {
            node.toFront();
        }
        initRender();
        initDoom();
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
            if (game.getPlayers().get(0).move((Node)img.getUserData())) {
                renderPlayer(game.getPlayers().get(0), img);
            }
        });
        HBox statusbox = new HBox();
        statusbox.setLayoutX(img.getLayoutX()+40);
        statusbox.setLayoutY(img.getLayoutY());
        statusbox.setPrefHeight(60);
        statusbox.setPrefWidth(40);
        statusbox.setMouseTransparent(true);
        statusbox.setUserData(link);
        statusbox.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
        elements.getChildren().add(statusbox);
        statusboxes.put(img,statusbox);
        return img;
    }
    private Group createHoodTile(String imgpath, int x, int y, Neighborhood hood){
        ImageView img = new ImageView(new Image(getClass().getResource(imgpath).toExternalForm()));
        img.setFitWidth(300);
        img.setFitHeight(300);
        img.setLayoutX(x);
        img.setLayoutY(y);
        elements.getChildren().add(img);
        //
        Button top = new Button(hood.getNodes().get(0).getName());
        Button mddl = new Button(hood.getNodes().get(1).getName());
        Button lower = new Button(hood.getNodes().get(2).getName());
        top.setOnAction(e->{
            if (game.getPlayers().get(0).move((Node)top.getUserData())) {
                renderPlayer(game.getPlayers().get(0), top);
            }
        });
        top.setLayoutX(img.getLayoutX()+100);
        top.setLayoutY(img.getLayoutY()+100);
        top.setUserData(hood.getNodes().get(0));
        HBox statusboxtop = new HBox();
        statusboxtop.setLayoutX(top.getLayoutX()+20);
        statusboxtop.setLayoutY(top.getLayoutY()-60);
        statusboxtop.setPrefHeight(60);
        statusboxtop.setPrefWidth(40);
        statusboxtop.setMouseTransparent(true);
        statusboxtop.setUserData(hood.getNodes().get(0));
        statusboxtop.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
        elements.getChildren().add(statusboxtop);
        statusboxes.put(top,statusboxtop);
        //
        mddl.setOnAction(e->{
            if (game.getPlayers().get(0).move((Node)mddl.getUserData())) {
                renderPlayer(game.getPlayers().get(0), mddl);
            }
        });
        mddl.setLayoutX(img.getLayoutX()+25);
        mddl.setLayoutY(img.getLayoutY()+200);
        mddl.setUserData(hood.getNodes().get(1));
        HBox statusboxmddl = new HBox();
        statusboxmddl.setLayoutX(mddl.getLayoutX()+20);
        statusboxmddl.setLayoutY(mddl.getLayoutY()-60);
        statusboxmddl.setPrefHeight(60);
        statusboxmddl.setPrefWidth(40);
        statusboxmddl.setMouseTransparent(true);
        statusboxmddl.setUserData(hood.getNodes().get(1));
        statusboxmddl.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
        elements.getChildren().add(statusboxmddl);
        statusboxes.put(mddl,statusboxmddl);
        //
        lower.setOnAction(e->{
            if (game.getPlayers().get(0).move((Node)lower.getUserData())) {
                renderPlayer(game.getPlayers().get(0), lower);
            }
        });
        lower.setLayoutX(img.getLayoutX()+200);
        lower.setLayoutY(img.getLayoutY()+200);
        lower.setUserData(hood.getNodes().get(2));
        HBox statusboxlower = new HBox();
        statusboxlower.setLayoutX(lower.getLayoutX()+20);
        statusboxlower.setLayoutY(lower.getLayoutY()-60);
        statusboxlower.setPrefHeight(60);
        statusboxlower.setPrefWidth(40);
        statusboxlower.setMouseTransparent(true);
        statusboxlower.setUserData(hood.getNodes().get(2));
        statusboxlower.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
        elements.getChildren().add(statusboxlower);
        statusboxes.put(lower,statusboxlower);
        //
        Group btngrp = new Group(top,mddl,lower);
        return new Group(img, btngrp);
    }
    private void initRender(){
        for (Investigator player:
             game.getPlayers()) {
            for (javafx.scene.Node node:
                 statusboxes.keySet()) {
                if (player.getSpace()==node.getUserData()){
                    renderPlayer(player,node);
                }
            }
        }
        for (Monster monster:
             game.getMonsters()){
            for (HBox node:
                    statusboxes.values()) {
                if (monster.getSpace()==node.getUserData()){
                    renderMonster(monster, (Node)node.getUserData());
                }
            }
        }
    }
    private void renderPlayer(Investigator player, javafx.scene.Node anchor) {
        for (HBox node:
             statusboxes.values()){
            for (javafx.scene.Node img:
                    node.getChildren()) {
                if (img.getUserData() == player){
                    node.getChildren().remove(img);
                    break;
                }
            }
        }
        ImageView img = new ImageView(new Image(getClass().getResource("/images/player_daniel.png").toExternalForm()));
        img.setFitHeight(60);
        img.setFitWidth(40);
        img.setUserData(player);
        statusboxes.get(anchor).getChildren().add(img);
    }
    public void renderMonster(Monster monster, Node destination){
        for (HBox node:
             statusboxes.values()) {
            for (javafx.scene.Node image:
                    node.getChildren()) {
                if (image.getUserData() == monster){
                    node.getChildren().remove(image);
                    break;
                }
            }
        }
        ImageView img = new ImageView(new Image(getClass().getResource("/images/monster_patrol.jpg").toExternalForm()));
        img.setFitHeight(60);
        img.setFitWidth(40);
        img.setUserData(monster);
        for (HBox node:
                statusboxes.values()){
            if ((Node)node.getUserData()==destination){
                node.getChildren().add(img);
            }
        }
    }
    public void renderDoom(Node space){
        ImageView img = new ImageView(new Image(getClass().getResource("/images/doom.png").toExternalForm()));
        img.setFitHeight(35);
        img.setFitWidth(35);
        img.setUserData("doom");
        for (HBox node:
                statusboxes.values()){
            if (node.getUserData()==space){
                node.getChildren().add(img);
            }
        }
    }
    public void destroyDoom(Node space){
        for (HBox node:
                statusboxes.values()){
            if (node.getUserData()==space){
                for (javafx.scene.Node image:
                        node.getChildren()) {
                    if (image.getUserData() == "doom"){
                        node.getChildren().remove(image);
                        break;
                    }
                }
            }
        }
    }
    private void initDoom(){
        for (Node node:
             net.getNodepile().values()) {
            for (int i = node.getDoom();i>0;i--){
                renderDoom(node);
            }
        }
    }
}
