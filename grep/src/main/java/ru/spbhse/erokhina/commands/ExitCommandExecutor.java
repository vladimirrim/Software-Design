package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * CommandExecutor for exit command. Exit command exits CLI. Does not require arguments.
 */
public class ExitCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) {
        environment.setPrevCommandOutputLines(new ArrayList<>());
        environment.setExitFlag(true);
    }
}
