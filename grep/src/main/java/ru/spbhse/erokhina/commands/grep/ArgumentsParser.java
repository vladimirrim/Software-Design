package ru.spbhse.erokhina.commands.grep;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.util.List;

/**
 * Class for parsing command line arguments for grep command.
 */
public class ArgumentsParser {
    /**
     * Parses command line arguments for grep command.
     * @param args list of arguments
     * @return parsed arguments in special format
     */
    public Arguments parse(List<String> args) throws ArgumentsParsingException {
        Arguments argsDescription = new Arguments();
        JCommander jCommander = JCommander.newBuilder().addObject(argsDescription).build();

        try {
            jCommander.parse(args.toArray(new String[0]));
        } catch (ParameterException e) {
            throw new ArgumentsParsingException(e);
        }

        return argsDescription;

    }
}