package ru.spbhse.erokhina.commands;

/**
 * Thrown when command is executed incorrectly.
 */
public class CommandExecutionException extends Exception {
    public CommandExecutionException(String message){
        super(message);
    }
}
