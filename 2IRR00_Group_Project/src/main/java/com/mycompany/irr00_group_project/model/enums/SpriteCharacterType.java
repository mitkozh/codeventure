package com.mycompany.irr00_group_project.model.enums;

/**
 * Enum representing different types of sprite characters available in the game.
 */
public enum SpriteCharacterType {
    ROBOT("Robot", "images/sprite/character_robot.png"),
    ROBOT_KID("Robot kid", "images/sprite/character_robot_kid.png"),
    ALIEN("Alien", "images/sprite/character_alien.png"),
    COOL_ALIEN("Cool alien", "images/sprite/character_alien_cool.png");

    private final String displayName;
    private final String imagePath;

    SpriteCharacterType(String displayName, String imagePath) {
        this.displayName = displayName;
        this.imagePath = imagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }

    /**
     * Gets the SpriteCharacterType by display name.
     * @param displayName the display name to search for
     * @return the matching SpriteCharacterType, or ROBOT as default
     */
    public static SpriteCharacterType fromDisplayName(String displayName) {
        for (SpriteCharacterType type : values()) {
            if (type.getDisplayName().equals(displayName)) {
                return type;
            }
        }
        return ROBOT; 
    }
}