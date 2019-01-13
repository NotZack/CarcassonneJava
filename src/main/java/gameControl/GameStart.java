package gameControl;

import UIControl.StaticUI;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import playerControl.CameraControl;
import playerControl.Input;
import playerControl.PlayerHandler;
import playerControl.Turn;
import tileControl.Tile;

public class GameStart extends Application {

    public static Pane root = new Pane();

    private static Scene primaryScene = new Scene(root, 800, 600);

    private static AnimationTimer gameLoop;

    private static long gameSpeed = 8_888_888;

    private void initGame() {

        BackgroundImage myBI= new BackgroundImage(new Image(
                "background.jpg",1000,667,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        root.setBackground(new Background(myBI));

        root.getChildren().add(Board.initBoard());
        Input.init(primaryScene);

        Tile.init();
        PlayerHandler.init();
        Turn.init();
        StaticUI.createUI();

        startLoop();
    }

    private static void startLoop() {

        //Timer runs constantly
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0 ;

            @Override
            public void handle(long frameTime) {
                if (frameTime - lastUpdate >= (gameSpeed) ) {
                    CameraControl.updateCamera();
                    lastUpdate = frameTime;
                }
            }
        };
        gameLoop.start();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Carcassonne");

        primaryStage.setScene(primaryScene);
        primaryStage.show();

        initGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
