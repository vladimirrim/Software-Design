package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CommandExecutor for ls command.Ls command prints files in current directory.
 */
public class LsCommandExecutor implements CommandExecutor {
    private static final String INVALID_NUMBER_OF_ARGS_MESSAGE = "Ls: too much arguments";
    private static final String INVALID_PATH_MESSAGE = "Ls: no such directory: ";

    @Override
    public void execute(List<String> args, Environment environment) throws IOException, CommandExecutionException {
        if (args.size() > 1) {
            throw new CommandExecutionException(INVALID_NUMBER_OF_ARGS_MESSAGE);
        } else if (args.isEmpty()) {
            environment.setPrevCommandOutputLines(Files.walk(Paths.get(environment.getCurrentDirectory()), 1).
                    skip(1).
                    map(path -> path.getFileName().toString()).
                    collect(Collectors.toList()));
        } else {
            String newDirectory = environment.getPathToFile(args.get(0));
            if (Files.isDirectory(Paths.get(newDirectory))) {
                environment.setCurrentDirectory(newDirectory);
                environment.setPrevCommandOutputLines(Files.walk(Paths.get(newDirectory), 1).
                        skip(1).
                        map(path -> path.getFileName().toString()).
                        collect(Collectors.toList()));
            } else {
                throw new CommandExecutionException(INVALID_PATH_MESSAGE + args.get(0));
            }
        }
    }
}
