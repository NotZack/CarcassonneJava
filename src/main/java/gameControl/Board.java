package gameControl;

import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import meepleControl.Meeple;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tileControl.Tile;

import java.util.ArrayList;

public class Board {

    private static Pane boardNode;

    private static ArrayList<Tile> placedTiles = new ArrayList<>();
    private static ArrayList<Point2D> possiblePlacements = new ArrayList<>();

    private static Group possiblePlacementRectangles = new Group();

    private static boolean possiblePlacement = true;

    static Pane initBoard() {
        boardNode = new Pane();

        boardNode.getChildren().add(possiblePlacementRectangles);
        possiblePlacementRectangles.setId("PossibleRectangles");

        return boardNode;
    }

    public static void removeMeeple(Meeple meepleToRemove) {
        if (boardNode.getChildren().contains(meepleToRemove))
            boardNode.getChildren().remove(meepleToRemove);
    }

    public static void placeTile(@NotNull Point2D coordinate, @NotNull Tile tileToPlace) {
        possiblePlacementRectangles.getChildren().clear();

        tileToPlace.setLayoutX(coordinate.getX());
        tileToPlace.setLayoutY(coordinate.getY());

        boardNode.getChildren().add(tileToPlace);
        placedTiles.add(tileToPlace);
    }

    public static void executePossiblePlacements(Tile tileToPlace) {
        possiblePlacements.clear();
        possiblePlacementRectangles.getChildren().clear();

        checkPlacement(tileToPlace);
    }

    private static void checkPlacement(Tile tileToPlace) {
        ArrayList<Point2D> possibleCoords = new ArrayList<>();
        ArrayList<Tile> possibleBuildOffs = new ArrayList<>();
        ArrayList<Point2D> notPossibleCoords = new ArrayList<>();

        for (int i = 0; i < placedTiles.size(); i++) {
            for (int j = 0; j < 4; j++) {
                if (Tile.sideMatches(placedTiles.get(i), tileToPlace, j)
                    && !notPossibleCoords.contains(getCoordFromSide(j, placedTiles.get(i)))) {
                    possibleBuildOffs.add(placedTiles.get(i));
                    possibleCoords.add(getCoordFromSide(j, placedTiles.get(i)));
                }
                else {
                    notPossibleCoords.add(getCoordFromSide(j, placedTiles.get(i)));
                }
            }
        }

        for (int s = 0; s < notPossibleCoords.size(); s++) {
            while (possibleCoords.contains(notPossibleCoords.get(s)))
                possibleCoords.remove(notPossibleCoords.get(s));
        }

        for (int t = 0; t < possibleCoords.size(); t++) {
            if (!possiblePlacements.contains(possibleCoords.get(t)))
                drawPossiblePlacement(possibleCoords.get(t));
        }
    }

    private static Point2D getCoordFromSide(int j, Tile tile) {

        int xOffset = 0;
        int yOffset = 0;
        switch (j) {
            case 0: yOffset = -110; break;
            case 1: xOffset = 110; break;
            case 2: yOffset = 110; break;
            case 3: xOffset = -110; break;
        }

        return new Point2D(tile.getLayoutX() + xOffset, tile.getLayoutY() + yOffset);
    }

    private static void drawPossiblePlacement(Point2D coordFromSide) {
        possiblePlacements.add(coordFromSide);

        Rectangle newRectangle = new Rectangle();
        newRectangle.setLayoutX(coordFromSide.getX());
        newRectangle.setLayoutY(coordFromSide.getY());
        newRectangle.setWidth(110);
        newRectangle.setHeight(110);
        newRectangle.setOpacity(0.5);
        possiblePlacementRectangles.getChildren().add(newRectangle);
    }

    @Contract(pure = true)
    public static Pane getBoardNode() {
        return boardNode;
    }
}
