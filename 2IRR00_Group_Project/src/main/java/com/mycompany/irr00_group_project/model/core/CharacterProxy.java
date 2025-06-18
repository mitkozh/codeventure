package com.mycompany.irr00_group_project.model.core;

import java.io.PrintWriter;
import java.util.Objects;

/**
 * Class used to print the commands in the in-game terminal.
 */
public class CharacterProxy implements Character {
    private PrintWriter commandSender;

    /**
     * Constructs a new CharacterProxy that will send commands to the specified PrintWriter.
     * 
     * @param commandSender the PrintWriter to which commands will be sent; must not be null
     * @throws NullPointerException if the specified commandSender is null
     */
    public CharacterProxy(PrintWriter commandSender) {
        this.commandSender = Objects.requireNonNull(commandSender, 
            "PrintWriter cannot be null");
    }

    @Override
    public void moveForward() {
        commandSender.println("CMD:MOVE_FORWARD");
    }

    @Override
    public void turnLeft() {
        commandSender.println("CMD:TURN_LEFT");

    }

    @Override
    public void turnRight() {
        commandSender.println("CMD:TURN_RIGHT");
    }
}
