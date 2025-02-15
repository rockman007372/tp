package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DECK_DESC_SOCIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DECK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deckcommands.AddDeckCommand;
import seedu.address.model.deck.Deck;

public class AddDeckCommandParserTest {
    private AddDeckCommandParser parser = new AddDeckCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Deck expectedDeck = new Deck("Sociology");

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DECK_DESC_SOCIOLOGY,
                new AddDeckCommand(expectedDeck));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Deck.MESSAGE_CONSTRAINTS, AddDeckCommand.MESSAGE_USAGE);

        // missing argument for deck name
        assertParseFailure(parser, INVALID_DECK_DESC, expectedMessage);
    }
}
