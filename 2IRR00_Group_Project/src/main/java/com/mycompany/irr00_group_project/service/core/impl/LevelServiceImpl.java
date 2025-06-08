package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.GameProgressDTO;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.resources.PersistenceService;
import com.mycompany.irr00_group_project.service.resources.impl.PersistenceServiceImpl;
import com.mycompany.irr00_group_project.utils.Constants;
import com.mycompany.irr00_group_project.utils.ParseUtils;

import java.io.IOException;
import java.util.*;

/**
 * Implementation of the LevelService interface.
 */
public class LevelServiceImpl implements LevelService {

    private final PersistenceService persistenceService;
    private final GameProgressDTO gameProgressDTO = new GameProgressDTO();

    /**
     * Default constructor that initializes the persistence service and loads the game progress.
     */
    public LevelServiceImpl() {
        this.persistenceService = new PersistenceServiceImpl(Constants.GAME_PROGRESS_FILE);
        loadProgress();
    }

    @Override
    public List<LevelDTO> getAllLevelsDTO() {
        List<LevelDTO> levels = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            LevelDTO levelProgress = getLevelProgress(i);
            levels.add(levelProgress);
        }
        return levels;
    }

    @Override
    public LevelData getLevelDataByFileName(String fileName) {
        try {
            return ParseUtils.parseLevel(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error loading level from file: " + fileName, e);
        }
    }

    @Override
    public void completeLevelAndSave(LevelDTO levelNewData) {
        int levelNumber = levelNewData.getLevelNumber();
        int levelNewDataStars = levelNewData.getStars();
        LevelDTO currentProgress = gameProgressDTO.getLevel(levelNumber);
        if (currentProgress == null || levelNewDataStars > currentProgress.getStars()) {
            gameProgressDTO.putLevel(levelNumber, levelNewData);
        }

        unlockNextLevel(levelNumber);
        saveProgress();
    }

    @Override
    public LevelDTO getLevelProgress(int levelNumber) {
        return gameProgressDTO.getLevelOrDefault(levelNumber);
    }

    @Override
    public void unlockNextLevel(int levelNumber) {
        int nextLevel = levelNumber + 1;
        if (!gameProgressDTO.containsLevel(nextLevel)) {
            gameProgressDTO.putLevel(nextLevel,
                    new LevelDTO(nextLevel, 0, true));
        }
    }

    @Override
    public boolean isLevelUnlocked(int levelNumber) {
        LevelDTO progress = gameProgressDTO.getLevel(levelNumber);
        return progress != null && progress.isUnlocked();
    }

    private void loadProgress() {
        Properties props = persistenceService.loadProperties();
        gameProgressDTO.putLevel(1, new LevelDTO(1,
                0, true));

        for (String key : props.stringPropertyNames()) {
            int levelBeginIndex = 6;
            if (key.startsWith("level_") && key.endsWith("_stars")) {
                try {
                    int levelNum = Integer.parseInt(
                            key.substring(levelBeginIndex, key.lastIndexOf("_")));
                    int stars = Integer.parseInt(
                            props.getProperty(key, "0"));
                    boolean unlocked = Boolean.parseBoolean(
                            props.getProperty("level_" + levelNum + "_unlocked", "false"));
                    gameProgressDTO.putLevel(levelNum,
                            new LevelDTO(levelNum, stars, unlocked));
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing progress data for: " + key);
                }
            }
        }
    }

    private void saveProgress() {
        Properties props = new Properties();
        for (Map.Entry<Integer, LevelDTO> entry : gameProgressDTO.getEntrySet()) {
            LevelDTO level = entry.getValue();
            props.setProperty("level_" + level.getLevelNumber() + "_stars",
                    String.valueOf(level.getStars()));
            props.setProperty("level_" + level.getLevelNumber() + "_unlocked",
                    String.valueOf(level.isUnlocked()));
        }

        persistenceService.saveProperties(props, "Game Progress Data");
    }
}
