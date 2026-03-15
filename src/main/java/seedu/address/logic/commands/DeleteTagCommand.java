package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes tag(s) from an existing person in the address book.
 */
public class DeleteTagCommand extends Command {

	public static final String COMMAND_WORD = "tag delete";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes tag(s) from a person in the address book. "
			+ "Parameters: "
			+ "INDEX (must be a positive integer) "
			+ PREFIX_TAG + "TAG (must be a non-empty string)\n"
			+ "Example: " + COMMAND_WORD + " "
			+ "1 "
			+ PREFIX_TAG + "Primary1 "
			+ PREFIX_TAG + "Mathematics";

	public static final String MESSAGE_SUCCESS = "Tag(s) removed from person: %1$s";
	public static final String MESSAGE_TAG_NOT_FOUND = "One or more specified tags do not exist for this person.";

	private final Index targetIndex;
	private final Set<Tag> tagsToDelete;

	public DeleteTagCommand(Index targetIndex, Set<Tag> tagsToDelete) {
		requireNonNull(targetIndex);
		requireNonNull(tagsToDelete);
		this.targetIndex = targetIndex;
		this.tagsToDelete = new HashSet<>(tagsToDelete);
	}

	@Override
	public CommandResult execute(Model model) throws CommandException {
		requireNonNull(model);
		List<Person> lastShownList = model.getFilteredPersonList();

		if (targetIndex.getZeroBased() >= lastShownList.size()) {
			throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
		}

		Person personToUpdate = lastShownList.get(targetIndex.getZeroBased());
		if (!personToUpdate.getTags().containsAll(tagsToDelete)) {
			throw new CommandException(MESSAGE_TAG_NOT_FOUND);
		}
		model.deleteTagsFromPerson(personToUpdate, tagsToDelete);
		return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToUpdate)));
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (!(other instanceof DeleteTagCommand)) {
			return false;
		}

		DeleteTagCommand otherDeleteTagCommand = (DeleteTagCommand) other;
		return targetIndex.equals(otherDeleteTagCommand.targetIndex)
				&& tagsToDelete.equals(otherDeleteTagCommand.tagsToDelete);
	}

	@Override
	public int hashCode() {
		return Objects.hash(targetIndex, tagsToDelete);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.add("targetIndex", targetIndex)
				.add("tagsToDelete", tagsToDelete)
				.toString();
	}

}
