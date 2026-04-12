package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds tags to one or more existing persons in the address book.
 */
public class AddTagCommand extends TagCommand {
    public static final String SUBCOMMAND_WORD = "add";
    public static final String COMMAND_PHRASE = TagCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Adds tag(s) to person(s) in the address book. "
            + "Parameters: "
            + "INDEX [INDEX]... (must be positive integers) "
            + PREFIX_TAG + "TAG (can contain any characters but must not be empty "
            + "and cannot have more than 20 characters.)\n"
            + "Example: " + COMMAND_PHRASE + " "
            + "1 2 "
            + PREFIX_TAG + "Primary 1 "
            + PREFIX_TAG + "Mathematics";

    public static final String MESSAGE_SUCCESS = "Added tags (%1$s) to student: %2$s";
    public static final String MESSAGE_BATCH_SUCCESS = "Added tags to students: %1$s";
    public static final String MESSAGE_TAG_ALREADY_EXISTS =
            "No students were updated because all specified tags already exist.";

    private final List<Person> affectedPersons = new ArrayList<>();
    private final List<Set<Tag>> tagsAddedByPerson = new ArrayList<>();

    /**
     * Creates an AddTagCommand to add tags to persons at {@code targetIndices}.
     */
    public AddTagCommand(List<Index> targetIndices, Set<Tag> tagsToAdd) {
        super(targetIndices, tagsToAdd);
    }

    @Override
    protected void checkPreconditions(List<Person> targetPersons) throws CommandException {
        affectedPersons.clear();
        tagsAddedByPerson.clear();
        Set<Tag> requestedTags = getTags();

        for (int i = 0; i < targetPersons.size(); i++) {
            Person person = targetPersons.get(i);
            Set<Tag> tagsToAdd = new HashSet<>(requestedTags);
            tagsToAdd.removeAll(person.getTags());

            if (!tagsToAdd.isEmpty()) {
                affectedPersons.add(person);
                tagsAddedByPerson.add(tagsToAdd);
            }
        }

        if (affectedPersons.isEmpty()) {
            throw new CommandException(MESSAGE_TAG_ALREADY_EXISTS);
        }
    }

    @Override
    protected void executeBatch(List<Person> targetPersons, Model model) {
        for (int i = 0; i < affectedPersons.size(); i++) {
            model.addTagsToPerson(affectedPersons.get(i), tagsAddedByPerson.get(i));
        }
    }


    @Override
    protected String formatSuccessMessage(List<Person> processedPersons) {
        if (affectedPersons.size() == 1) {
            return String.format(MESSAGE_SUCCESS,
                    formatTags(tagsAddedByPerson.get(0)), affectedPersons.get(0).getName());
        }
        return String.format(MESSAGE_BATCH_SUCCESS, formatPersonTagChanges(affectedPersons, tagsAddedByPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddTagCommand)) {
            return false;
        }
        AddTagCommand otherTagCommand = (AddTagCommand) other;
        return getTargetIndices().equals(otherTagCommand.getTargetIndices())
            && getTags().equals(otherTagCommand.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTargetIndices(), getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndices", getTargetIndices())
            .add("tagsToAdd", getTags())
            .toString();
    }
}
