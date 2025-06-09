package com.mycompany.irr00_group_project.model.core.dto;

/**
 * GameSettingsDTO represents the data transfer object for game settings.
 */
public class GameSettingsDTO {
    private String selectedAvatar;
    private double masterVolume;
    private double musicVolume;
    private double sfxVolume;

    /**
     * Default constructor initializes the game settings with default values.
     */
    public GameSettingsDTO() {
        this.selectedAvatar = "Robot";
        this.masterVolume = 1.0;
        this.musicVolume = 1.0;
        this.sfxVolume = 1.0;
    }

    /**
     * Constructor to initialize game settings with specific values.
     *
     * @param selectedAvatar the selected avatar
     * @param masterVolume   the master volume level
     * @param musicVolume    the music volume level
     * @param sfxVolume      the sound effects volume level
     */
    public GameSettingsDTO(String selectedAvatar, double masterVolume,
                           double musicVolume, double sfxVolume) {
        this.selectedAvatar = selectedAvatar;
        this.masterVolume = masterVolume;
        this.musicVolume = musicVolume;
        this.sfxVolume = sfxVolume;
    }

    public String getSelectedAvatar() {
        return selectedAvatar;
    }

    public void setSelectedAvatar(String selectedAvatar) {
        this.selectedAvatar = selectedAvatar;
    }

    public double getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(double masterVolume) {
        this.masterVolume = masterVolume;
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume;
    }

    public double getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(double sfxVolume) {
        this.sfxVolume = sfxVolume;
    }
}