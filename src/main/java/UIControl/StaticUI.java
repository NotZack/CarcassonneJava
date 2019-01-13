package UIControl;

import gameControl.Board;
import gameControl.GameStart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import playerControl.Player;
import playerControl.Turn;
import tileControl.Tile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StaticUI {

    private static Pane meeplePane = new Pane();

    private static boolean meepleSelectionOpen = false;

    private static int placedFollowerCount = 0;
    private static int notPlacedFollowerCount = 0;

    private static Text playerTurnText = new Text("Player 0's turn");
    private static Text tilesLeft = new Text("0 Tiles Left");

    private static Tile tileToPlace = Turn.getTurnTile();

    private static Text placedFollowerText = new Text("Placed Followers: 0");
    private static Text notPlacedFollowerText = new Text("Idle Followers: 0");

    private static Text currentPlayerPoints = new Text("Score 0");

    private static Button placeMeeple = new Button("Place Meeple");
    private static Button rotateTile = new Button("Rotate Tile");
    private static Button completeTurn = new Button("Complete Turn");

    private static Rectangle background = new Rectangle(0, 500, 800, 100);

    public static void createUI() {

        background.setFill(Color.color(0,0,0.11));

        playerTurnText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        playerTurnText.setFill(Color.RED);
        playerTurnText.setX(5);
        playerTurnText.setY(20);

        tilesLeft.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        tilesLeft.setFill(Color.RED);
        tilesLeft.setX(5);
        tilesLeft.setY(40);

        placedFollowerText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        placedFollowerText.setFill(Color.RED);
        placedFollowerText.setX(20);
        placedFollowerText.setY(530);

        notPlacedFollowerText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        notPlacedFollowerText.setFill(Color.RED);
        notPlacedFollowerText.setX(40);
        notPlacedFollowerText.setY(550);

        currentPlayerPoints.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        currentPlayerPoints.setFill(Color.RED);
        currentPlayerPoints.setX(205);
        currentPlayerPoints.setY(550);

        completeTurn.setLayoutX(205);
        completeTurn.setLayoutY(560);

        placeMeeple.setLayoutX(50);
        placeMeeple.setLayoutY(560);

        rotateTile.setLayoutX(530);
        rotateTile.setLayoutY(520);

        tileToPlace.setLayoutX(620);
        tileToPlace.setLayoutY(490);

        placeMeeple.setOnAction((event) -> {
            if (!meepleSelectionOpen)
                createMeepleSelection();
            else
                closeMeepleSelection();
        });

        rotateTile.setOnAction((event) -> {
            tileToPlace.rotateSides();
            Board.executePossiblePlacements(tileToPlace);
        });

        completeTurn.setOnAction((event) -> {
            Turn.passTurn();
        });

        GameStart.root.getChildren().addAll(
                background, playerTurnText, tilesLeft, placedFollowerText, notPlacedFollowerText, currentPlayerPoints,
                placeMeeple, rotateTile, tileToPlace, completeTurn
        );
        GameStart.root.getChildren().add(meeplePane);

        updateUI();
    }

    private static void closeMeepleSelection() {
        meepleSelectionOpen = false;
        meeplePane.getChildren().clear();
    }

    private static void createMeepleSelection() {
        meepleSelectionOpen = true;
        meeplePane.setId("MeeplePane");

        ImageView knightMeeple = null;
        ImageView farmMeeple = null;
        ImageView cloisterMeeple = null;
        ImageView highwaymanMeeple = null;

        try {
            knightMeeple = new ImageView(new Image(new FileInputStream("src/main/resources/meeples/KnightMeeple.png")));
            farmMeeple = new ImageView(new Image(new FileInputStream("src/main/resources/meeples/FarmMeeple.png")));
            highwaymanMeeple = new ImageView(new Image(new FileInputStream("src/main/resources/meeples/HighwayManMeeple.png")));
            cloisterMeeple = new ImageView(new Image(new FileInputStream("src/main/resources/meeples/CloisterMeeple.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Rectangle background = new Rectangle(0, 0, 800, 100);
        Label selectMeeple = new Label("Select the meeple \nyou would like to \nplace");

        background.setFill(Color.BLACK);

        selectMeeple.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        selectMeeple.setTextFill(Color.RED);
        selectMeeple.setLayoutX(15);
        selectMeeple.setLayoutY(25);

        knightMeeple.setLayoutX(180);
        knightMeeple.setLayoutY(0);

        farmMeeple.setLayoutX(290);
        farmMeeple.setLayoutY(0);

        cloisterMeeple.setLayoutX(400);
        cloisterMeeple.setLayoutY(0);

        highwaymanMeeple.setLayoutX(510);
        highwaymanMeeple.setLayoutY(0);


        meeplePane.getChildren().addAll(
                background, selectMeeple, knightMeeple, farmMeeple, cloisterMeeple, highwaymanMeeple
        );
    }

    public static void updateUI() {
        Player currentPlayer = Turn.getCurrentTurn();
        tileToPlace = Turn.getTurnTile();

        if (!GameStart.root.getChildren().contains(tileToPlace))
            GameStart.root.getChildren().add(tileToPlace);

        playerTurnText.setText("Player " + Turn.getTurnNumber() + "'s turn");
        tilesLeft.setText(Tile.getNumTilesLeft() + " tiles left");

        placedFollowerCount = currentPlayer.getPlacedMeeplesNum();
        notPlacedFollowerCount = currentPlayer.getNotPlacedMeeplesNum();
        placedFollowerText.setText("Placed Followers: " + placedFollowerCount);
        notPlacedFollowerText.setText("Idle Followers: " + notPlacedFollowerCount);

        currentPlayerPoints.setText("Points: " + currentPlayer.getPoints());

        StaticUI.tileReloaded();
    }

    public static void tilePlaced() {
        rotateTile.setDisable(true);
        completeTurn.setDisable(false);
        placeMeeple.setDisable(false);
    }

    private static void tileReloaded() {
        rotateTile.setDisable(false);
        placeMeeple.setDisable(true);
        completeTurn.setDisable(true);

        tileToPlace.setLayoutX(620);
        tileToPlace.setLayoutY(490);
    }
}
