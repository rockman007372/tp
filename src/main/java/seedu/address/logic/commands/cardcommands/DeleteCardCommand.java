package seedu.address.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;

/**
 * Deletes a card identified using it's displayed index from the selected deck.
 */
public class DeleteCardCommand extends Command {

    public static final String COMMAND_WORD = "deleteCard";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the card identified by the index number used in the displayed selected deck.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CARD_SUCCESS = "Deleted Card: %1$s";

    private final Index targetIndex;

    public DeleteCardCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Card> lastShownList = model.getFilteredCardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        Card cardToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCard(cardToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CARD_SUCCESS, cardToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCardCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCardCommand) other).targetIndex)); // state check
    }
}
