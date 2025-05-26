package com.mycompany.irr00_group_project.view.components;

import com.mycompany.irr00_group_project.model.enums.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * SpriteCharacterView is responsible for displaying the sprite character.
 */
public class SpriteCharacterView {
    private ImageView spriteImageView;
    private Image sprite;

    /**
     * Constructs a SpriteCharacterView and initializes the sprite image.
     */
    public SpriteCharacterView() {
        initializeSprite();
        loadSpriteImages();
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
                break;
            case EAST:
            default:
                spriteImageView.setScaleX(1);
                break;
        }
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
