package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.util.List;

/**
 * Class for executing a command.
 */
public interface CommandExecutor {

    /**
     * Method for executing command.
     *
     * @param args arguments for this command
     * @param environment given environment
     */
    void execute(List<String> args, Environment environment) throws IOException;

}
