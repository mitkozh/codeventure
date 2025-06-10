package com.mycompany.irr00_group_project.model.core.dto;

/**
 * Data Transfer Object for Level information.
 * This class is used to transfer level data for the preview level button.
 */
public class LevelDTO {
    private int levelNumber;
    private int stars;
    private boolean unlocked;

    /**
     * Constructor to initialize LevelDTO.
     *
     * @param levelNumber the level number
     * @param stars       the number of stars earned in the level
     * @param unlocked    whether the level is unlocked or not
     */
    public LevelDTO(int levelNumber, int stars, boolean unlocked) {
        this.levelNumber = levelNumber;
        this.stars = stars;
        this.unlocked = unlocked;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
    
    public int getStars() {
        return stars;
    }
    
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * Sets the stars for the level.
     * @param stars the number of stars to set
     * @pre {@code stars} must be non-negative.
     */
    public void setStars(int stars) {
        if (stars < 0) {
            throw new IllegalArgumentException("Stars cannot be negative");
        }
        this.stars = stars;
    }
}
