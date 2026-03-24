package seedu.address.logic.parser;

import seedu.address.logic.commands.ConfirmCommand;

/**
 * Parses input arguments and creates a new ConfirmCommand object.
 */
public class ConfirmCommandParser implements Parser<ConfirmCommand> {

    @Override
    public ConfirmCommand parse(String args) {
        return new ConfirmCommand(args.trim());
    }
}
