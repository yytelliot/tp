package seedu.address.logic.commands;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes one or more persons identified using their displayed indices from the address book.
 */
public class DeleteCommand extends BatchCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person(s) identified by the index number(s) used in the displayed person list.\n"
            + "Parameters: INDEX [INDEX]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_PERSONS_SUCCESS = "Deleted %1$d persons: %2$s";

    /**
     * Creates a DeleteCommand to delete persons at {@code targetIndices}.
     */
    public DeleteCommand(List<Index> targetIndices) {
        super(targetIndices);
    }

    @Override
    protected void executeBatch(List<Person> targetPersons, Model model) throws CommandException {
        for (Person person : targetPersons) {
            model.deletePerson(person);
        }
    }

    @Override
    protected String formatSuccessMessage(List<Person> processedPersons) {
        if (processedPersons.size() == 1) {
            return String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(processedPersons.get(0)));
        }
        return String.format(MESSAGE_DELETE_PERSONS_SUCCESS, processedPersons.size(), joinNames(processedPersons));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return getTargetIndices().equals(otherDeleteCommand.getTargetIndices());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTargetIndices());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", getTargetIndices())
                .toString();
    }
}
