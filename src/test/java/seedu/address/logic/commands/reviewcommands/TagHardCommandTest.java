package seedu.address.logic.commands.reviewcommands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.reviewcommands.TagHardCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalCards.getTypicalMasterDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TagHardCommand.
 * Fifth deck chosen (math) as one card, prevent random shuffling.
 */
public class TagHardCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMasterDeck(), new UserPrefs());
        model.selectDeck(INDEX_FIFTH);
        model.reviewDeck(INDEX_FIFTH, List.of(new Tag.TagName[]{Tag.TagName.UNTAGGED}));
    }

    @Test
    public void execute_tagCardInReviewSuccess() {
        String expectedMessage = MESSAGE_SUCCESS;
        Model expectedModel = new ModelManager(getTypicalMasterDeck(), new UserPrefs());
        expectedModel.selectDeck(INDEX_FIFTH);
        expectedModel.reviewDeck(INDEX_FIFTH, List.of(new Tag.TagName[]{Tag.TagName.UNTAGGED}));
        expectedModel.tagCurrentCardInReview(new Tag(Tag.TagName.HARD));
        assertCommandSuccess(new TagHardCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        TagHardCommand tagHardCommand = new TagHardCommand();

        // same object -> returns true
        assertTrue(tagHardCommand.equals(tagHardCommand));

        // different types -> returns false
        assertTrue(!tagHardCommand.equals(1));

        // null -> returns false
        assertTrue(!tagHardCommand.equals(null));
    }
}
