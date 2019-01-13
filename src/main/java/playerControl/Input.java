package playerControl;

import UIControl.StaticUI;
import gameControl.Board;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Input {

    public static void init(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

            KeyCode keyPressed = key.getCode();

            //DecideActions
            if (keyPressed.equals(KeyCode.W)) CameraControl.setNorth(true);
            if (keyPressed.equals(KeyCode.A)) CameraControl.setWest(true);
            if (keyPressed.equals(KeyCode.S)) CameraControl.setSouth(true);
            if (keyPressed.equals(KeyCode.D)) CameraControl.setEast(true);
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            KeyCode keyReleased = key.getCode();

            //DecideActions
            if (keyReleased.equals(KeyCode.W)) CameraControl.setNorth(false);
            if (keyReleased.equals(KeyCode.A)) CameraControl.setWest(false);
            if (keyReleased.equals(KeyCode.S)) CameraControl.setSouth(false);
            if (keyReleased.equals(KeyCode.D)) CameraControl.setEast(false);
        });

        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getTarget() instanceof Rectangle && ((Rectangle) mouseEvent.getTarget()).getParent().getId() != null) {
                if (((Rectangle) mouseEvent.getTarget()).getParent().getId().equals("PossibleRectangles")) {
                    Board.placeTile(new Point2D(((Rectangle) mouseEvent.getTarget()).getLayoutX(), ((Rectangle) mouseEvent.getTarget()).getLayoutY()), Turn.getTurnTile());
                    StaticUI.tilePlaced();
                }

            }
        });
    }
}
