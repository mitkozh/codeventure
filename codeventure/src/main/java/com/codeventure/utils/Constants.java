package com.codeventure.utils;

/**
 * This class contains constants used throughout the application.
 */
public class Constants {
    public static final int LEVELS_PER_PAGE = 20;
    public static final String SHARED_JAR_PATH = "/com/codeventure"
            + "/assets/jars/shared.jar";
    public static final String USER_CODE_PACKAGE = "player";
    public static final String USER_CODE_CLASS_NAME = "PlayerCode";
    public static final String USER_CODE_FQN = USER_CODE_PACKAGE + "." + USER_CODE_CLASS_NAME;
    public static final String TEMP_OUTPUT_BASE_DIR = "temp_user_compiled_code";
    public static final String INITIAL_IMPORTS_CODE = """
            package player;
            
            import com.codeventure.service.sandbox.UserCode;
            import com.codeventure.model.core.Character;
           
            """;
    public static final String DEFAULT_CODE =
                    """
                    public class PlayerCode implements UserCode {
                        @Override
                        public void execute(Character character) {
                            character.turnRight();
                            character.moveForward();
                        }
                    }
                    """;
    public static final String GAME_SETTINGS_FILE =  "game_settings.properties";
    public static final String GAME_PROGRESS_FILE = "game_progress.properties";
    public static final String BACKGROUND_MUSIC_LOCATION = "/com/codeventure"
            + "/assets/sounds/MainMenuMusic.wav";
    public static final int MAX_LEVEL = 50; 

}
