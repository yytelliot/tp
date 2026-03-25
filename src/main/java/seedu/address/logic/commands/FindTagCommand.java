package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagsContainKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all tags in address book that match the argument keyword.
 * Keyword matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String SUBCOMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = SUBCOMMAND_WORD + ": Finds all tags that match the specified keyword "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + SUBCOMMAND_WORD + " important";

    private final TagsContainKeywordsPredicate predicate;

    public FindTagCommand(TagsContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindTagCommand)) {
            return false;
        }
        FindTagCommand otherFindTagCommand = (FindTagCommand) other;
        return predicate.equals(otherFindTagCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

    
}
