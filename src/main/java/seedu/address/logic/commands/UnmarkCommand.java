package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Marks one or more students' payment status as unpaid.
 */
public class UnmarkCommand extends BatchCommand {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the payment status of the student(s) identified by index number(s)"
            + " in the displayed student list as unpaid.\n"
            + "Parameters: INDEX [INDEX]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_UNMARK_PERSON_SUCCESS = "Marked student as unpaid: %1$s";
    public static final String MESSAGE_UNMARK_PERSONS_SUCCESS = "Marked %1$d students as unpaid: %2$s";
    public static final String MESSAGE_ALREADY_UNPAID = "This student has already been marked as unpaid: %1$s";
    public static final String MESSAGE_ALREADY_UNPAID_PLURAL =
            "These students have already been marked as unpaid: %1$s";

    /**
     * Creates an UnmarkCommand to mark the persons at {@code targetIndices} as unpaid.
     */
    public UnmarkCommand(List<Index> targetIndices) {
        super(targetIndices);
    }

    @Override
    protected void checkPreconditions(List<Person> targetPersons) throws CommandException {
        List<Index> distinctTargetIndices = getDistinctTargetIndices();
        List<String> alreadyUnpaidNames = new ArrayList<>();
        for (int i = 0; i < targetPersons.size(); i++) {
            Person person = targetPersons.get(i);
            if (!person.isPaid()) {
                Index index = distinctTargetIndices.get(i);
                alreadyUnpaidNames.add("(" + index.getOneBased() + ") " + person.getName());
            }
        }
        if (!alreadyUnpaidNames.isEmpty()) {
            String names = String.join(", ", alreadyUnpaidNames);
            String message = alreadyUnpaidNames.size() == 1
                    ? String.format(MESSAGE_ALREADY_UNPAID, names)
                    : String.format(MESSAGE_ALREADY_UNPAID_PLURAL, names);
            throw new CommandException(message);
        }
    }

    @Override
    protected void executeBatch(List<Person> targetPersons, Model model) {
        for (Person targetPerson : targetPersons) {
            boolean isPaid = false;
            Person unmarkedPerson = new Person(
                    targetPerson.getName(), targetPerson.getPhone(), targetPerson.getEmail(),
                    targetPerson.getAddress(), targetPerson.getDay(), targetPerson.getStartTime(),
                    targetPerson.getEndTime(), targetPerson.getRate(), isPaid, targetPerson.getTags());
            model.setPerson(targetPerson, unmarkedPerson);
        }
    }

    @Override
    protected String formatSuccessMessage(List<Person> processedPersons) {
        if (processedPersons.size() == 1) {
            return String.format(MESSAGE_UNMARK_PERSON_SUCCESS, Messages.format(processedPersons.get(0)));
        }
        return String.format(MESSAGE_UNMARK_PERSONS_SUCCESS, processedPersons.size(), joinNames(processedPersons));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnmarkCommand)) {
            return false;
        }
        UnmarkCommand otherUnmarkCommand = (UnmarkCommand) other;
        return getTargetIndices().equals(otherUnmarkCommand.getTargetIndices());
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
