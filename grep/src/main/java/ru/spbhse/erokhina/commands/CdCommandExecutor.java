package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * CommandExecutor for cd command.Cd command changes current directory to the given one.
 */
public class CdCommandExecutor implements CommandExecutor {
    private static final String INVALID_NUMBER_OF_ARGS_MESSAGE = "Cd: too much arguments";
    private static final String INVALID_PATH_MESSAGE = "Cd: no such directory: ";

    @Override
    public void execute(List<String> args, Environment environment) throws CommandExecutionException {
        if (args.size() > 1) {
            throw new CommandExecutionException(INVALID_NUMBER_OF_ARGS_MESSAGE);
        } else if (args.isEmpty()) {
            environment.setCurrentDirectory(environment.getHomeDirectory());
            environment.setPrevCommandOutputLines(Collections.emptyList());
        } else {
            String newDirectory = environment.getPathToFile(args.get(0));
            if (Files.isDirectory(Paths.get(newDirectory))) {
                environment.setCurrentDirectory(newDirectory);
                environment.setPrevCommandOutputLines(Collections.emptyList());
            } else {
                throw new CommandExecutionException(INVALID_PATH_MESSAGE + args.get(0));
            }
        }
    }
}
