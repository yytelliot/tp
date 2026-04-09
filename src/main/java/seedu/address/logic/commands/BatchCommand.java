package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Abstract base class for commands that operate on one or more persons identified by index.
 * Uses the Template Method pattern to centralise batch execution logic.
 */
public abstract class BatchCommand extends Command {

    private final List<Index> targetIndices;

    /**
     * Creates a BatchCommand targeting persons at the given indices.
     */
    protected BatchCommand(List<Index> targetIndices) {
        requireNonNull(targetIndices);
        this.targetIndices = new ArrayList<>(targetIndices);
    }

    @Override
    public final CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        validateIndices(lastShownList);
        List<Person> targetPersons = collectPersons(lastShownList);
        checkPreconditions(targetPersons);
        executeBatch(targetPersons, model);
        return new CommandResult(formatSuccessMessage(targetPersons));
    }

    private void validateIndices(List<Person> lastShownList) throws CommandException {
        List<Integer> invalidIndices = new ArrayList<>();
        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                invalidIndices.add(index.getOneBased());
            }
        }
        if (!invalidIndices.isEmpty()) {
            String indices = invalidIndices.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ": " + indices);
        }
    }

    private List<Person> collectPersons(List<Person> lastShownList) {
        List<Person> persons = new ArrayList<>();
        for (Index index : getDistinctTargetIndices()) {
            persons.add(lastShownList.get(index.getZeroBased()));
        }
        return persons;
    }

    /**
     * Validates domain-specific preconditions before executing the batch.
     * Default is no-op. Override to add checks (e.g. "already marked as paid").
     */
    protected void checkPreconditions(List<Person> targetPersons) throws CommandException {
        // No-op by default
    }

    /**
     * Executes the batch operation on the collected persons.
     * Each subclass implements its specific model mutation.
     */
    protected abstract void executeBatch(List<Person> targetPersons, Model model) throws CommandException;

    /**
     * Formats the success message for the completed batch operation.
     */
    protected abstract String formatSuccessMessage(List<Person> processedPersons);

    /**
     * Returns an unmodifiable view of the target indices.
     */
    protected List<Index> getTargetIndices() {
        return Collections.unmodifiableList(targetIndices);
    }

    /**
     * Returns the target indices with duplicates removed while preserving input order.
     */
    protected List<Index> getDistinctTargetIndices() {
        return targetIndices.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Utility: joins person names with their indices in the format "(index) name", comma separated.
     */
    protected String joinNamesWithIndices(List<Person> persons) {
        List<Index> indices = getDistinctTargetIndices();
        List<String> parts = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            parts.add("(" + indices.get(i).getOneBased() + ") " + persons.get(i).getName());
        }
        return String.join(", ", parts);
    }
}
