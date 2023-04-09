package seedu.address.model.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.card.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalCards;

public class ReviewTest {

    private final Deck DEFAULT = new Deck("DEFAULT");
    private final List<Card> TYPICAL_CARDS = TypicalCards.getTypicalCards();

    @Test
    public void flipCurrCardTest() {
        Review reviewInTest = new Review(DEFAULT, TYPICAL_CARDS, -1);
        Card modelCardFlipped = reviewInTest.getCurrCardFlipped();
        Card modelCardUnflipped = reviewInTest.getCurrCard();

        // Flip the current card to show answer
        reviewInTest.flipCurrCard();
        Card currFlippedCard = reviewInTest.getCurrCard();

        // Unflip the curr card again
        reviewInTest.flipCurrCard();
        Card currUnflippedCard = reviewInTest.getCurrCard();

        assertEquals(modelCardFlipped, currFlippedCard);
        assertEquals(modelCardUnflipped, currUnflippedCard);
    }

    @Test
    void isCurrCardFlippedTest() {
        Review reviewInTest = new Review(DEFAULT, TYPICAL_CARDS, -1);
        assertFalse(reviewInTest.isCurrCardFlipped());

        reviewInTest.flipCurrCard();
        assertTrue(reviewInTest.isCurrCardFlipped());
    }

    @Test
    void goToNextCard_isNotLastCard_returnsTrue() {
        Review reviewInTest = new Review(DEFAULT, TYPICAL_CARDS, -1);
        assertTrue(reviewInTest.goToNextCard());
    }

    @Test
    void goToNextCard_isLastCard_returnsFalse() {
        Review reviewWithOneCard = new Review(DEFAULT, TYPICAL_CARDS.subList(0, 1), -1);
        assertFalse(reviewWithOneCard.goToNextCard());
    }

    @Test
    void goToPrevCard_isNotFirstCard_returnsTrue() {
        Review reviewInTest = new Review(DEFAULT, TYPICAL_CARDS, -1);
        reviewInTest.goToNextCard();
        assertTrue(reviewInTest.goToPrevCard());
    }

    @Test
    void goToPrevCard_isFirstCard_returnsFalse() {
        Review reviewWithOneCard = new Review(DEFAULT, TYPICAL_CARDS.subList(0, 1), -1);
        assertFalse(reviewWithOneCard.goToPrevCard());
    }

    @Test
    void goToNextCard_goToPrevCard_SameReviewState() {
        Review fixedOrderReview = new Review(DEFAULT, TYPICAL_CARDS, -1, true);
        Review modelReview = new Review(DEFAULT, TYPICAL_CARDS, -1, true);

        // Go forward, then backward
        fixedOrderReview.goToNextCard();
        fixedOrderReview.goToPrevCard();
        assertEquals(modelReview, fixedOrderReview);
    }

    @Test
    void tagCurrentCardTest() {
        List<Card> listWithOneCard = TYPICAL_CARDS.subList(0, 1);
        Tag easy = new Tag(Tag.TagName.EASY);
        Card modelEasyCard = listWithOneCard.get(0).buildCardWithtag(easy);

        // Tags the current card in the list as easy
        Review reviewWithOneCard = new Review(DEFAULT, listWithOneCard, -1);
        reviewWithOneCard.tagCurrentCard(easy);
        Card currTaggedCard = reviewWithOneCard.getCurrCardFlipped();

        assertEquals(modelEasyCard, currTaggedCard);
    }

    @Test
    void getNoOfEasyTagsTest() {
        int easyTagCount = (int) TYPICAL_CARDS.stream().filter(card -> card.getTagName().equals("easy")).count();
        Review reviewInTest = new Review(DEFAULT, TYPICAL_CARDS, -1);
        assertEquals(reviewInTest.getNoOfEasyTags(), easyTagCount);
    }

    @Test
    void getNoOfMediumTagsTest() {
        int easyTagCount = (int) TYPICAL_CARDS.stream().filter(card -> card.getTagName().equals("medium")).count();
        Review reviewInTest = new Review(DEFAULT, TYPICAL_CARDS, -1);
        assertEquals(reviewInTest.getNoOfMediumTags(), easyTagCount);
    }

    @Test
    void getNoOfHardTagsTest() {
        int easyTagCount = (int) TYPICAL_CARDS.stream().filter(card -> card.getTagName().equals("hard")).count();
        Review reviewInTest = new Review(DEFAULT, TYPICAL_CARDS, -1);
        assertEquals(reviewInTest.getNoOfHardTags(), easyTagCount);
    }

}
