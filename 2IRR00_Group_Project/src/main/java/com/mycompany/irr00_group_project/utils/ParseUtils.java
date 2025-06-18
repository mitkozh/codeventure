package com.mycompany.irr00_group_project.utils;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.TileType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility class for parsing different kinds of files.
 */
public class ParseUtils {

    /**
     * Parses a level file and returns a LevelData object.
     * @param levelDTO the name of the level file to parse
     * @return a LevelData object containing the parsed level data
     * @throws IOException if a file cannot be found or read
     */
    public static LevelData parseLevel(LevelDTO levelDTO)
            throws IOException {
        LevelData levelData = new LevelData();
        String levelFileName = "level" + levelDTO.getLevelNumber() + ".txt";
        InputStream inputStream = ParseUtils.class
                .getResourceAsStream("/com/mycompany/irr00_group_project/data/levels/"
                        + levelFileName);
        if (inputStream == null) {
            throw new IOException("Could not find level file: " + levelFileName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            String currentSection = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                if (line.equals("OPTIMAL_STEPS")) {
                    String stepsLine = reader.readLine();
                    if (stepsLine != null) {
                        levelData.setOptimalSteps(Integer.parseInt(stepsLine.trim()));
                    }
                    continue;
                }
                
                if (line.equals("OBSTACLES") || line.equals("DOORS") || line.equals("KEYS")) {
                    currentSection = line;
                    continue;
                }

                String[] parts = line.split(" ");

                handleIndividualPart(parts, levelData, currentSection);
                levelData.addDoorKeyPairs();
            }
        }

        return levelData;
    }

    /**
     * Handles individual parts of the level file based on the current section.
     * @param parts the parts of the line split by spaces
     * @param levelData the LevelData object to populate
     * @param currentSection the current section being processed
     */
    private static void handleIndividualPart(String[] parts,
            LevelData levelData, String currentSection) {
        switch (parts[0]) {
            case "SIZE":
                if (parts.length >= 3) {
                    int width = Integer.parseInt(parts[1]);
                    int height = Integer.parseInt(parts[2]);
                    levelData.setSize(width, height);
                }
                break;

            case "START":
                if (parts.length >= 4) {
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    Direction direction = Direction.valueOf(parts[3]);
                    levelData.setStart(row, col, direction);
                }
                break;

            case "END":
                if (parts.length >= 3) {
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    levelData.setEnd(row, col);
                }
                break;

            default:
                handleDifferentTileTypes(parts, levelData, currentSection);
                break;
        }
    }

    /**
     * Handles different tile types based on the current section.
     * @param parts the parts of the line split by spaces
     * @param levelData the LevelData object to populate
     * @param currentSection the current section being processed
     */
    private static void handleDifferentTileTypes(String[] parts,
            LevelData levelData, String currentSection) {
        if (parts.length >= 2 && !currentSection.isEmpty()) {
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);

            switch (currentSection) {
                case "OBSTACLES":
                    levelData.setTile(row, col, TileType.OBSTACLE);
                    break;
                case "DOORS":
                    levelData.setTile(row, col, TileType.DOOR_CLOSED);
                    levelData.addDoor(row, col);
                    break;
                case "KEYS":
                    levelData.setTile(row, col, TileType.KEY);
                    levelData.addKey(row, col);
                    break;
                default:
                    break;
            }
        }
    }
}
