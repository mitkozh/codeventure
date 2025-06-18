package com.mycompany.irr00_group_project.controller;

import java.util.List;

import com.mycompany.irr00_group_project.gui.components.LevelPreviewButton;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.navigator.LevelSelectionScreenNavigatorManager;
import com.mycompany.irr00_group_project.utils.Constants;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;

/**
 * Controller for the level selection screen.
 * This class is responsible for paginating the levels and displaying them in a
 * grid format.
 */
public class LevelSelectionController {

    private Pagination pagination;

    private final LevelService levelService = LevelServiceImpl.getInstance();
    private List<LevelDTO> allLevelsDTO;

    private LevelSelectionScreenNavigatorManager navigatorManager;

    /**
     * This method is called when the controller is initialized.
     * It retrieves all levels and sets up the pagination.
     */
    public void initialize() {
        navigatorManager = new LevelSelectionScreenNavigatorManager();
        allLevelsDTO = levelService.getAllLevelsDTO();
        int pageCount = (int) Math.ceil((double) allLevelsDTO.size() / Constants.LEVELS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    /**
     * Creates a page of level selection buttons for the pagination control.
     * @param pageIndex The index of the page to create
     * @return The created page as a JavaFX Node
     */
    private Node createPage(int pageIndex) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        int buttonCol = 0;
        int buttonRow = 0;
        int beginPageIdx = pageIndex * Constants.LEVELS_PER_PAGE;
        int endPageIdx = Math.min(beginPageIdx + Constants.LEVELS_PER_PAGE, allLevelsDTO.size());
        for (int i = beginPageIdx; i < endPageIdx; i++) {
            LevelDTO level = allLevelsDTO.get(i);
            LevelPreviewButton button = new LevelPreviewButton();
            button.setLevelNumber(level.getLevelNumber());
            button.setStars(level.getStars());
            button.setUnlocked(level.isUnlocked());
            button.setOnAction(event -> selectLevel(level));
            grid.add(button, buttonCol, buttonRow);
            buttonCol++;
            if (buttonCol > 3) {
                buttonCol = 0;
                buttonRow++;
            }
        }
        return grid;
    }

    /**
     * Handles level selection by the user.
     * @param level The selected level DTO
     */
    private void selectLevel(LevelDTO level) {
        levelService.selectLevel(level);
        loadLevel();
    }

    /**
     * Loads the selected level.
     */
    private void loadLevel() {
        navigatorManager.navigateToLevel();
    }

    /**
     * This method is called when the main menu button is clicked.
     * It navigates back to the main menu screen.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void backToMenu(ActionEvent actionEvent) {
        goToMenu();
    }

    /**
     * Navigates back to the main menu screen.
     */
    public void goToMenu() {
        navigatorManager.navigateToMenu();
    }

    /**
     * Sets the pagination control for level selection.
     * @param pagination The pagination control to use
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
