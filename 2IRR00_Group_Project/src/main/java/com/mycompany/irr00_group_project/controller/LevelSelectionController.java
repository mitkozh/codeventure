package com.mycompany.irr00_group_project.controller;

import java.io.IOException;
import java.util.List;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.LevelService;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.service.observable.LevelSelectionObservables;
import com.mycompany.irr00_group_project.utils.Constants;
import com.mycompany.irr00_group_project.utils.NavigationManager;
import com.mycompany.irr00_group_project.view.components.LevelPreviewButton;
import com.mycompany.irr00_group_project.view.screen.GameScreen;
import com.mycompany.irr00_group_project.view.screen.MainMenuScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;

/**
 * Controller for the level selection screen.
 * This class is responsible for paginating the levels and displaying them in a
 * grid format.
 */
public class LevelSelectionController {

    @FXML
    private Pagination pagination;

    private final LevelService levelService = LevelServiceImpl.getInstance();
    private List<LevelDTO> allLevelsDTO;

    /**
     * This method is called when the controller is initialized.
     * It retrieves all levels and sets up the pagination.
     */
    @FXML
    public void initialize() {
        allLevelsDTO = levelService.getAllLevelsDTO();
        int pageCount = (int) Math.ceil((double) allLevelsDTO.size() / Constants.LEVELS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

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

    private void selectLevel(LevelDTO level) {
        levelService.selectLevel(level);
        loadLevel();
    }

    private void loadLevel() {
        GameScreen gameScreen = new GameScreen();
        try {
            NavigationManager.getInstance().navigateTo(gameScreen.getView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the main menu button is clicked.
     * It navigates back to the main menu screen.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    public void backToMenu(ActionEvent actionEvent) {
        goToMenu();
    }

    @FXML
    private static void goToMenu() {
        try {
            MainMenuScreen menuScreen = new MainMenuScreen();
            NavigationManager.getInstance().navigateTo(menuScreen.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
