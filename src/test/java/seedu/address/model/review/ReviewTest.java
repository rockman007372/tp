package seedu.address.model.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.card.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.TypicalCards;

public class ReviewTest {

    private final Deck PROGRAMMING = new Deck("Programming Concepts");
    private List<Card> cardsInProgrammingDeck = TypicalCards.getTypicalCardInTheSameDeck();

    private int seed = 1;

    @Test
    public void flipCurrCardTest() {
        Review reviewInTest = new Review(PROGRAMMING, cardsInProgrammingDeck, -1);
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
    void isCurrCardFlipped() {
        Review reviewInTest = new Review(PROGRAMMING, cardsInProgrammingDeck, -1);
        assertFalse(reviewInTest.isCurrCardFlipped());

        reviewInTest.flipCurrCard();
        assertTrue(reviewInTest.isCurrCardFlipped());
    }

    @Test
    void goToNextCard_isNotLastCard_returnsTrue() {
        Review reviewInTest = new Review(PROGRAMMING, cardsInProgrammingDeck, -1);
        assertTrue(reviewInTest.goToNextCard());
    }

    @Test
    void goToNextCard_isLastCard_returnsFalse() {
        Review reviewWithOneCard = new Review(PROGRAMMING, cardsInProgrammingDeck.subList(0, 1), -1);
        assertFalse(reviewWithOneCard.goToNextCard());
    }

    @Test
    void goToNextCard_isNotLastCard_unflipLastCard() {
    }

    @Test
    void goToPrevCard() {
    }

    @Test
    void getCurrCard() {
    }

    @Test
    void tagCurrentCard() {
    }

    @Test
    void getReviewStatsList() {
    }

    @Test
    void getNoOfEasyTags() {
    }

    @Test
    void getNoOfMediumTags() {
    }

    @Test
    void getNoOfHardTags() {
    }

    @Test
    void getReviewDeckNameList() {
    }

    @Test
    void updateReviewStatsList() {
    }

}
