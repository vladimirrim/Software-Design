package ru.spbhse.erokhina.parser;

import java.util.List;

/**
 * Class which contains the result of parsing - a command between pipes. It contains the name of parsed command and its arguments.
 */
public class ParsedCommand {
    private final String command;
    private final List<String> args;

    ParsedCommand(String command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    /**
     * Gets command name.
     * @return command name
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the list of arguments.
     * @return the list of arguments
     */
    public List<String> getArgs() {
        return args;
    }
}