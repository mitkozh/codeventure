package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.utils.ParseUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the LevelService interface.
 */
public class LevelServiceImpl implements LevelService {
    @Override
    public List<LevelDTO> getAllLevelsDTO() {
        Random rand = new Random();
        List<LevelDTO> levels = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            // Simulate random stars and unlocked status for each level
            if (i <= 10) {
                int stars = rand.nextInt(1, 4);
                levels.add(new LevelDTO(i, stars, true));
            } else {
                levels.add(new LevelDTO(i, 0, false));
            }
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
}
