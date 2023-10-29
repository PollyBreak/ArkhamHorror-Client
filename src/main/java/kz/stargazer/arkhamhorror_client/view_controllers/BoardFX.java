package kz.stargazer.arkhamhorror_client.view_controllers;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import kz.stargazer.arkhamhorror_client.brd.Board;
import kz.stargazer.arkhamhorror_client.brd.Neighborhood;
import kz.stargazer.arkhamhorror_client.brd.Node;
import org.controlsfx.control.cell.ImageGridCell;

public class BoardFX {
    private Board net;
    private final String north_path = "C:\\javaprog\\arkham_project\\ArkhamHorror-Client\\src\\main\\resources\\views\\nort-map.png";
    private final String down_path = north_path;
    private final String east_path = north_path;
    private final String merch_path = north_path;
    private final String river_path = north_path;
    private final String st_lane_path = north_path;
    private final String st_park_path = north_path;
    private final String st_bridge_path = north_path;
    public BoardFX(Board brd){net=brd;}
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
        ImageView img = new ImageView(new Image(imgpath));
        img.setFitWidth(w);
        img.setFitHeight(h);
        img.setLayoutX(x);
        img.setLayoutY(y);
        img.setOnMouseClicked(e->{
            img.setRotate(link.getName().length());
        });
        return img;
    }
    private Group createHoodTile(String imgpath, int x, int y, Neighborhood hood){
        ImageView img = new ImageView(new Image(imgpath));
        img.setFitWidth(300);
        img.setFitHeight(300);
        img.setLayoutX(x);
        img.setLayoutY(y);
        Button top = new Button(hood.getNodes().get(0).getName());
        Button mddl = new Button(hood.getNodes().get(1).getName());
        Button lower = new Button(hood.getNodes().get(2).getName());
        top.setOnAction(e->{
            top.setText("test");
        });
        top.setLayoutX(img.getLayoutX()+50);
        top.setLayoutY(img.getLayoutY()+30);
        mddl.setOnAction(e->{
            mddl.setText("test");
        });
        mddl.setLayoutX(img.getLayoutX()+50);
        mddl.setLayoutY(img.getLayoutY()+150);
        lower.setOnAction(e->{
            lower.setText("test");
        });
        lower.setLayoutX(img.getLayoutX()+50);
        lower.setLayoutY(img.getLayoutY()+270);
        Group btngrp = new Group(top,mddl,lower);
        return new Group(img, btngrp);
    }
}
