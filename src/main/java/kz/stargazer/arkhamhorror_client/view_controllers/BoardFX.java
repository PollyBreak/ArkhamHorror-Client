package kz.stargazer.arkhamhorror_client.view_controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;
import kz.stargazer.arkhamhorror_client.Heroes.Investigator;
import kz.stargazer.arkhamhorror_client.Heroes.monsters.Monster;
import kz.stargazer.arkhamhorror_client.Mechanics.Game;
import kz.stargazer.arkhamhorror_client.brd.Board;
import kz.stargazer.arkhamhorror_client.brd.Neighborhood;
import kz.stargazer.arkhamhorror_client.brd.Node;

import java.util.HashMap;
import java.util.function.UnaryOperator;

public class BoardFX {
    private Game game;
    private Board net;
    private Group elements = new Group();
    private HashMap<javafx.scene.Node,HBox> statusboxes = new HashMap<>();
    private HashMap<Neighborhood,StackPane> hoodboxes = new HashMap<>();
    private ListView<String> stats = new ListView<>();
    private Label messageline;
    private HBox monsterhand = new HBox();
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
    public HBox build(){
        Group biggroup = new Group();
        switch (net.getScenario()){
            case ("Azatoth") -> biggroup = buildBase_Azathoth();
        }
        elements.getChildren().add(biggroup);
        monsterhand.setLayoutX(1000);
        monsterhand.setLayoutY(500);
        elements.getChildren().add(monsterhand);
        //
        ScrollPane pane = new ScrollPane(elements);
        pane.setPrefSize(Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
        pane.setPannable(true);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for (javafx.scene.Node node:
             statusboxes.values()) {
            node.toFront();
        }
        for (javafx.scene.Node node:
                hoodboxes.values()) {
            node.toFront();
        }
        //
        initRender();
        initDoom();
        //
        HBox bigbox = new HBox(pane,buildActionbox());
        return bigbox;
    }
    private Group buildBase_Azathoth(){
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
        return new Group(st1,st2,st3,st4,st5,st6,st7,northside,downtown,easttown,merchant,rivertown);
    }
    private VBox buildActionbox(){
        messageline = new Label("");
        messageline.setWrapText(true);
        Button wardbtn = new Button("Ward");
        wardbtn.setOnAction(e->{
            if (game.getPlayers().get(0).ward()){
                int res = game.getPlayers().get(0).getTEMP_true_successes();
                messageline.setText("While testing your lore skills you've thrown "+String.valueOf(res)+" successes!");
                for (int i =0;i<res;i++) {
                    destroyDoom(game.getPlayers().get(0).getSpace());
                }
            }
        });
        Button moneybtn = new Button("Gather Resources");
        moneybtn.setOnAction(e->{
            game.getPlayers().get(0).gatherMoney();
            updateStats();
            messageline.setText("You've earned 1$!");
        });
        //
        ImageView sheet = new ImageView(new Image(getClass().getResource("/images/daniel_sheet.jpg").toExternalForm()));
        sheet.setFitHeight(400);
        sheet.setFitWidth(200);
        stats.setMaxHeight(100);
        VBox box = new VBox(sheet,wardbtn,moneybtn,stats,messageline);
        box.setPrefWidth(400);
        box.setAlignment(Pos.CENTER);
        return box;
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
            Node destination = game.getPlayers().get(0).move((Node)img.getUserData());
            if (destination!=null) {
                renderPlayer(game.getPlayers().get(0), destination);
                updateStats();
            }
        });
        //
        createNodeStatbox(img,40,0);
        //
        return img;
    }
    private Group createHoodTile(String imgpath, int x, int y, Neighborhood hood){
        ImageView img = new ImageView(new Image(getClass().getResource(imgpath).toExternalForm()));
        img.setFitWidth(300);
        img.setFitHeight(300);
        img.setLayoutX(x);
        img.setLayoutY(y);
        //
        Button top = createHoodNodeInteraction(img.getLayoutX()+100,img.getLayoutY()+100,hood.getNodes().get(0));
        Button mddl = createHoodNodeInteraction(img.getLayoutX()+25,img.getLayoutY()+200,hood.getNodes().get(1));
        Button lower = createHoodNodeInteraction(img.getLayoutX()+200,img.getLayoutY()+200,hood.getNodes().get(2));
        createNodeStatbox(top,20,-60);
        createNodeStatbox(mddl,20,-60);
        createNodeStatbox(lower,20,-60);
        //
        createHoodStatbox(img.getLayoutX()+120,img.getLayoutY()+150,hood);
        //
        Group btngrp = new Group(top,mddl,lower);
        return new Group(img, btngrp);
    }

    private void createNodeStatbox(javafx.scene.Node anchor,double offsetX,double offsetY){
        HBox statusbox = new HBox();
        statusbox.setLayoutX(anchor.getLayoutX()+offsetX);
        statusbox.setLayoutY(anchor.getLayoutY()+offsetY);
        statusbox.setUserData(anchor.getUserData());
        statusbox.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
        elements.getChildren().add(statusbox);
        statusboxes.put(anchor,statusbox);
    }
    private void createHoodStatbox(double x, double y,Neighborhood anchor){
        StackPane hoodbox = new StackPane();
        hoodbox.setAlignment(Pos.TOP_LEFT);
        hoodbox.setMouseTransparent(true);
        hoodbox.setLayoutX(x);
        hoodbox.setLayoutY(y);
        hoodbox.setUserData(anchor);
        elements.getChildren().add(hoodbox);
        hoodboxes.put(anchor,hoodbox);

    }
    private Button createHoodNodeInteraction(double x, double y, Node node){
        Button btn = new Button(node.getName());
        btn.setOnAction(e->{
            Node destination = game.getPlayers().get(0).move((Node)btn.getUserData());
            if (destination!=null) {
                renderPlayer(game.getPlayers().get(0), destination);
                updateStats();
            }
        });
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setUserData(node);
        return btn;
    }
    private void initRender(){
        for (Investigator player:
             game.getPlayers()) {
            renderPlayer(player,player.getSpace());
        }
        updateStats();
        for (Monster monster:
             game.getMonsters()){
            for (HBox node:
                    statusboxes.values()) {
                if (monster.getSpace()==node.getUserData()){
                    monster.getSpace().addMonster(monster);
                    renderMonster(monster, (Node)node.getUserData());
                }
            }
        }
    }
    private void renderPlayer(Investigator player, Node destination) {
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
        for (HBox node:
                statusboxes.values()){
            if (node.getUserData()==destination){
                node.getChildren().add(img);
                break;
            }
        }
    }
    public void renderMonster(Monster monster, Node destination){
        destroyMonster(monster);
        ImageView img = new ImageView(new Image(getClass().getResource("/images/monsters/"+monster.getName()+".jpg").toExternalForm()));
        img.setFitHeight(60);
        img.setFitWidth(40);
        img.setUserData(monster);
        for (HBox node:
                statusboxes.values()){
            if ((Node)node.getUserData()==destination){
                node.getChildren().add(img);
            }
        }
        //
        //ZOOM/CLICK EVENTS
        //
        img.setOnMouseExited(e->{
            elements.getChildren().remove(elements.getChildren().size()-1);
        });
        img.setOnMouseEntered(e->{
            ImageView zoom = new ImageView(img.getImage());
            zoom.setFitWidth(img.getFitWidth());
            zoom.setFitHeight(img.getFitHeight());
            zoom.setLayoutX(img.getParent().getLayoutX());
            zoom.setLayoutY(img.getParent().getLayoutY()+100);
            zoom.setScaleX(4);
            zoom.setScaleY(4);
            zoom.setMouseTransparent(true);
            elements.getChildren().add(zoom);
        });
        img.setOnMouseClicked(e->{
            ImageView zoom = new ImageView(new Image(getClass().getResource("/images/monsters/"+monster.getName()+"_back.jpg").toExternalForm()));
            zoom.setFitWidth(img.getFitWidth());
            zoom.setFitHeight(img.getFitHeight());
            zoom.setLayoutX(img.getParent().getLayoutX());
            zoom.setLayoutY(img.getParent().getLayoutY()+100);
            zoom.setScaleX(4);
            zoom.setScaleY(4);
            zoom.setMouseTransparent(true);
            elements.getChildren().remove(elements.getChildren().size()-1);
            elements.getChildren().add(zoom);
        });
    }
    public void destroyMonster(Monster monster){
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
    }
    public void placeMonsterToHand(Monster monster){
        destroyMonster(monster);
        ImageView img = new ImageView(new Image(getClass().getResource("/images/monsters/"+monster.getName()+".jpg").toExternalForm()));
        img.setFitHeight(240);
        img.setFitWidth(160);
        img.setUserData(monster);
        monsterhand.getChildren().add(img);
        img.setOnMouseExited(e->{
            img.setImage(new Image(getClass().getResource("/images/monsters/"+monster.getName()+".jpg").toExternalForm()));
        });
        img.setOnMouseEntered(e->{
            img.setImage(new Image(getClass().getResource("/images/monsters/"+monster.getName()+"_back.jpg").toExternalForm()));
            img.setOnMouseClicked(ex->{
                game.getPlayers().get(0).hit((Monster)img.getUserData());
            });
        });
    }
    public void removeMonsterFromHand(Monster monster){
        for (javafx.scene.Node image:
                monsterhand.getChildren()) {
            if (image.getUserData() == monster){
                monsterhand.getChildren().remove(image);
                break;
            }
        }
    }
    public void renderDoom(Node space){
        ImageView img = new ImageView(new Image(getClass().getResource("/images/doom.png").toExternalForm()));
        img.setFitHeight(35);
        img.setFitWidth(35);
        img.setRotate(Math.random()*180);
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
    public void renderClue(Neighborhood hood){
        int dynamicOffsetX = 15;
        int dymanicOffsetY = -10;
        ImageView img = new ImageView(new Image(getClass().getResource("/images/doom.png").toExternalForm()));
        img.setFitHeight(35);
        img.setFitWidth(35);
        img.setRotate(Math.random()*180);
        img.setUserData("clue");
        for (StackPane node:
                hoodboxes.values()){
            if (node.getUserData()==hood){
                node.getChildren().add(img);
                if (node.getChildren().size() > 1) {
                    if((node.getChildren().size()-1)%3==0){
                        StackPane.setMargin(img, new Insets(node.getChildren().size()*-4, 0, 0, node.getChildren().size()*-4));
                        continue;
                    }
                    int lastIndex = node.getChildren().size() - 2;
                    double lastX = StackPane.getMargin(node.getChildren().get(lastIndex)).getLeft();
                    double lastY = StackPane.getMargin(node.getChildren().get(lastIndex)).getTop();
                    StackPane.setMargin(img, new javafx.geometry.Insets(lastY + dymanicOffsetY, 0, 0, lastX + dynamicOffsetX));
                }else {
                    StackPane.setMargin(img, new Insets(0, 0, 0, 0));
                }
            }
        }
    }
    public void destroyClue(Neighborhood hood){
        for (StackPane node:
                hoodboxes.values()){
            if (node.getUserData()==hood){
                for (javafx.scene.Node image:
                        node.getChildren()) {
                    if (image.getUserData() == "clue"){
                        node.getChildren().remove(image);
                        break;
                    }
                }
            }
        }
    }
    public void renderAnomaly(Neighborhood hood){
        ImageView img = new ImageView(new Image(getClass().getResource("/images/doom.png").toExternalForm()));
        img.setFitHeight(120);
        img.setFitWidth(120);
        img.setRotate(Math.random()*180);
        img.setUserData("anomaly");
        for (StackPane node:
                hoodboxes.values()) {
            if (node.getUserData() == hood) {
                node.getChildren().add(img);
                StackPane.setMargin(img, new Insets(-60, 0, 0, -40));
                img.toBack();
                break;
            }
        }
    }
    public void destroyAnomaly(Neighborhood hood){
        for (StackPane node:
                hoodboxes.values()){
            if (node.getUserData()==hood){
                for (javafx.scene.Node image:
                        node.getChildren()) {
                    if (image.getUserData() == "anomaly"){
                        node.getChildren().remove(image);
                        break;
                    }
                }
            }
        }
    }
    public void updateStats(){
        stats.getItems().clear();
        stats.getItems().add("Name - "+game.getPlayers().get(0).getName());
        stats.getItems().add("Health - "+String.valueOf(game.getPlayers().get(0).getHealth()));
        stats.getItems().add("Sanity - "+String.valueOf(game.getPlayers().get(0).getSanity()));
        stats.getItems().add("Money - "+String.valueOf(game.getPlayers().get(0).getMoney()));
    }
    public void leaveMessage(String msg){
        messageline.setText(msg);
    }
}
