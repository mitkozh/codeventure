package com.mycompany.irr00_group_project.model.core;

import java.io.PrintWriter;

public class CharacterProxy implements Character {
    private PrintWriter commandSender;

    public CharacterProxy(PrintWriter commandSender) {
        this.commandSender = commandSender;
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
