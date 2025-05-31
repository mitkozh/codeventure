package com.mycompany.irr00_group_project.view.components;

import com.mycompany.irr00_group_project.model.enums.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * SpriteCharacterView is responsible for displaying the sprite character.
 */
public class SpriteCharacterView {
    private ImageView spriteImageView;
    private Image sprite;

    private Polygon directionArrow;
    private StackPane stackPane;

    /**
     * Constructs a SpriteCharacterView and initializes the sprite image.
     */
    public SpriteCharacterView() {
        initializeSprite();
        loadSpriteImages();
        createDirectionArrow();
        stackPane = new StackPane(spriteImageView, directionArrow);
        directionArrow.setMouseTransparent(true);
    }

    private void createDirectionArrow() {
    directionArrow = new Polygon();
    // Points for an upward-pointing triangle (relative to center)
    directionArrow.getPoints().addAll(
        0.0, -20.0,   // Top corner
        10.0, 0.0,    // Bottom right corner
        -10.0, 0.0    // Bottom left corner
    );
    directionArrow.setFill(Color.WHITE);
    directionArrow.setOpacity(0.3);
    directionArrow.setStroke(Color.BLACK);
    directionArrow.setStrokeWidth(2);
    directionArrow.setTranslateY(+10);
}

    private void initializeSprite() {
        spriteImageView = new ImageView();
        spriteImageView.setFitWidth(50);
        spriteImageView.setFitHeight(50);
        spriteImageView.getStyleClass().add("sprite-character");
    }

    private void loadSpriteImages() {
        try {
            sprite = new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream("/com/mycompany/irr00_group_project/assets/"
                            + "images/sprite/character_robot.png")));
        } catch (Exception e) {
            System.err.println("Could not load sprite images");
        }
    }

    /**
     * Updates the sprite image and direction based on the given direction.
     *
     * @param direction the direction to update the sprite to
     */
    public void updateDirection(Direction direction) {
    spriteImageView.setImage(sprite);
    switch (direction) {
        case WEST:
            spriteImageView.setScaleX(-1);
            directionArrow.setRotate(270);
            break;
        case EAST:
            spriteImageView.setScaleX(1);
            directionArrow.setRotate(90);
            break;
        case NORTH:
            directionArrow.setRotate(0);
            break;
        case SOUTH:
            directionArrow.setRotate(180);
            break;
        default:
            directionArrow.setRotate(0);
            break;
    }
}

public StackPane getNode() {
    return stackPane;
}

    /**
     * Returns the ImageView of the sprite character.
     *
     * @return the ImageView of the sprite character
     */
    public ImageView getImageView() {
        return spriteImageView;
    }

}
