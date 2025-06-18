package com.mycompany.irr00_group_project.gui.components;

import java.util.Objects;

import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.SpriteCharacterType;
import com.mycompany.irr00_group_project.service.core.SettingsService;
import com.mycompany.irr00_group_project.service.core.impl.SettingsServiceImpl;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * SpriteCharacterView is responsible for displaying and managing the visual 
 * representation of a sprite character, including its image and direction indicator.
 * Handles sprite loading, sizing, and directional display updates.
 */
public class SpriteCharacterView {
    private ImageView spriteImageView;
    private Image sprite;
    private Polygon directionArrow;
    private StackPane stackPane;
    private SettingsService settingsService;
    private int minTileSize = 40;

    /**
     * Constructs a SpriteCharacterView and initializes all visual components.
     * Creates the sprite image view, loads the initial sprite image,
     * and sets up the direction indicator arrow.
     */
    public SpriteCharacterView() {
        settingsService = SettingsServiceImpl.getInstance();
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
                0.0, -20.0, // Top corner
                10.0, 0.0, // Bottom right corner
                -10.0, 0.0 // Bottom left corner
        );
        directionArrow.setFill(Color.WHITE);
        directionArrow.setOpacity(0.3);
        directionArrow.setStroke(Color.BLACK);
        directionArrow.setStrokeWidth(2);
        directionArrow.setTranslateY(+10);
    }

    private void initializeSprite() {
        spriteImageView = new ImageView();
        spriteImageView.setFitWidth(minTileSize);
        spriteImageView.setFitHeight(minTileSize);
        spriteImageView.getStyleClass().add("sprite-character");
    }

    /**
     * Updates the sprite size based on the current grid size.
     * Ensures the sprite maintains proper proportions relative to the game grid.
     * 
     * @param gridSize The current size of the game grid
     */
    public void updateSpriteSize(int gridSize) {
        double curSize = Math.max(minTileSize, minTileSize * (8.0 / gridSize));
        spriteImageView.setFitWidth(curSize);
        spriteImageView.setFitHeight(curSize);
        updateArrowSize(curSize);
    }

    /**
     * Adjusts the direction arrow size proportionally to the sprite size.
     * 
     * @param curSpriteSize The current size of the sprite
     */
    private void updateArrowSize(double curSpriteSize) {
        double arrowScale = curSpriteSize / minTileSize;
        double arrowHeight = 20.0 * arrowScale;
        double arrowWidth = 10.0 * arrowScale;

        directionArrow.getPoints().clear();
        directionArrow.getPoints().addAll(
                0.0, -arrowHeight,
                arrowWidth, 0.0,
                -arrowWidth, 0.0);
        directionArrow.setStrokeWidth(2 * arrowScale);
        directionArrow.setTranslateY(10 * arrowScale);
    }

    /**
     * Loads the sprite image based on the currently selected avatar from settings.
     * Handles image loading errors gracefully by printing stack traces.
     */
    private void loadSpriteImages() {
        try {
            String avatar = settingsService.getSelectedAvatar();
            SpriteCharacterType characterType = SpriteCharacterType.fromDisplayName(avatar);
            sprite = new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream("/com/mycompany/irr00_group_project/assets/"
                            + characterType.getImagePath())));
            spriteImageView.setImage(sprite);
        } catch (Exception e) {
            System.err.println("Could not load sprite images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the sprite image and direction based on the given direction.
     * 
     * @param direction the direction to update the sprite to.
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

    /**
     * Updates the sprite image based on the selected avatar in settings.
     */
    public void updateSpriteImage() {
        loadSpriteImages();
        spriteImageView.setImage(sprite);
    }
}
