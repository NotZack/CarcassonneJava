package tileControl;

import com.google.common.collect.*;
import com.sun.istack.internal.NotNull;
import gameControl.Board;
import javafx.geometry.Point2D;
import meepleControl.Meeple;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.Contract;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Tile extends ImageView {

    private static Random random = new Random();

    private static ArrayList<Tile> notPlacedTiles = new ArrayList<>();
    private static Tile startingTile;

    private ArrayList<Character> sides = new ArrayList<>();
    private ArrayList<Boolean> connections = new ArrayList<>();

    private boolean monistary = false;
    private boolean town = false;
    private boolean pennant = false;

    Tile(Image sprite) {
        this.setImage(sprite);
    }

    Tile(@NotNull Tile existingTile) {
        this.connections = existingTile.connections;
        this.sides = existingTile.sides;
        this.setImage(existingTile.getImage());
    }

    public static void init() {
        loadTiles();
        createInitialTile();
    }

    private static void loadTiles() {
        try {
            Files.walk(Paths.get("src/main/resources/tiles")).filter(Files::isRegularFile).forEach(spritePath -> {
                try {
                    String spriteName = spritePath.getFileName().toString().substring(0, spritePath.getFileName().toString().indexOf("."));


                    for (int i = 0; i < getFrequency(spriteName); i++) {
                        Tile newTile = new Tile((new Image(new FileInputStream(spritePath.toString()),0, 0, true, false)));

                        if (spriteName.charAt(0) == 'M') newTile.monistary = true;
                        else if (spriteName.charAt(0) == 'P') newTile.pennant = true;
                        else if (spriteName.charAt(0) == 'T') newTile.town = true;

                        for (int j = 1; j < spriteName.length(); j++) {
                            newTile.sides.add(spriteName.charAt(j));
                            newTile.connections.add(false);
                        }

                        if (spriteName.equals("BCRFR"))
                            startingTile = newTile;

                        notPlacedTiles.add(newTile);
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Contract(pure = true)
    private static int getFrequency(@org.jetbrains.annotations.NotNull @NotNull String tileName) {

        switch (tileName) {
            case "BCCFC": return 3;
            case "BCCFF": return 5;
            case "BCCRC": return 1;
            case "BCCRR": return 3;
            case "BCFCF": return 3;
            case "BCFFF": return 5;
            case "BCFRR": return 3;
            case "BCRFR": return 4;
            case "BCRRF": return 3;
            case "BFCFC": return 1;
            case "BFFRR": return 9;
            case "BFRFR": return 8;

            case "MFFFF": return 4;
            case "MFFRF": return 2;

            case "PCCCC": return 1;
            case "PCCFC": return 1;
            case "PCCFF": return 2;
            case "PCCRC": return 2;
            case "PCCRR": return 2;
            case "PFCFC": return 2;

            case "TCRRR": return 3;
            case "TFRRR": return 4;
            case "TRRRR": return 1;
        }
        return 0;
    }

    private static void createInitialTile() {
        Board.placeTile(new Point2D(345, 195), startingTile);
    }

    @NotNull
    @Contract(" -> new")
    public static Tile createRandomTile() {
        int randIndex = random.nextInt(notPlacedTiles.size());
        Tile randTile = new Tile(notPlacedTiles.get(randIndex));
        notPlacedTiles.remove(randIndex);
        return randTile;
    }

    public static int getNumTilesLeft() {
        return notPlacedTiles.size();
    }

    public static boolean connectionMatches(Tile tileToCheck, Tile tileToPlace, int sideToCheck) {
        return !(Boolean) tileToCheck.getConnections().get(sideToCheck);
    }

    public static boolean sideMatches(Tile tileToCheck, Tile tileToPlace, int sideToCheck) {
        return tileToCheck.getSides().get(sideToCheck).equals(tileToPlace.getSides().get(getOppositeSide(sideToCheck)));
    }

    public Meeple removeMeeple() {
        return null;
    }

    public void rotateSides() {
        sides.add(0, sides.get(3));
        sides.remove(4);

        connections.add(0, connections.get(3));
        connections.remove(4);

        if (this.getRotate() % 360 == 0)
            this.setRotate(0);

        this.setRotate(this.getRotate() + 90);
    }

    public ArrayList getConnections() {
        return connections;
    }

    public ArrayList getSides() {
        return sides;
    }

    public static int getOppositeSide(int j) {
        switch (j) {
            case 0: return 2;
            case 1: return 3;
            case 2: return 0;
            case 3: return 1;
        }
        return 0;
    }
}
