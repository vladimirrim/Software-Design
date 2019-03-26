package ru.spbhse.erokhina.commands.grep;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing parsed arguments of grep command. Uses JCommander to parse arguments.
 */
public class Arguments {
    @Parameter(names = {"-i"}, description = "Perform case insensitive matching. By default, grep is case sensitive.")
    private boolean ignoreCaseFlag = false;

    @Parameter(names = {"-w"}, description = "The expression is searched for as a word.")
    private boolean wordRegexpFlag = false;

    @Parameter(names = {"-A"},
            description = "Print num lines of trailing context after each match.",
            validateWith = PositiveInteger.class)
    private Integer linesAfterContextNum = 0;

    public static class PositiveInteger implements IParameterValidator {
        public void validate(String name, String value) throws ParameterException {
            int n = Integer.parseInt(value);
            if (n < 0) {
                throw new ParameterException("Parameter " + name + " should be positive.");
            }
        }
    }

    @Parameter(description = "Pattern and file name.", validateWith = ParametersValidator.class)
    private List<String> parameters = new ArrayList<>();

    public static class ParametersValidator implements IParameterValidator {
        public void validate(String name, String value) throws ParameterException {
            int length = value.split(" ").length;

            if (length == 0) {
                throw new ParameterException("Pattern should be specified.");
            }

            if (length > 2) {
                throw new ParameterException("Unexpected parameter(s).");
            }
        }
    }

    public boolean isIgnoreCaseFlag() {
        return ignoreCaseFlag;
    }

    public boolean isWordRegexpFlag() {
        return wordRegexpFlag;
    }

    public Integer getLinesAfterContextNum() {
        return linesAfterContextNum;
    }

    public String getPattern() {
        if (parameters.size() == 0) {
            return null;
        }
        return parameters.get(0);
    }

    public String getFileName() {
        if (parameters.size() < 2) {
            return null;
        }
        return parameters.get(1);
    }
}