package structures.basic;

import allCards.BadOmen;
import org.junit.Assert;
import org.junit.Test;
import utils.OrderedCardLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CardConverterTest {

    @Test
    public void cardDifferentiator() {
        Player player1 = new Player(true);
        CardConverter cardConverter = new CardConverter(player1);
        List<Card> cards = new ArrayList<Card>(OrderedCardLoader.getPlayer1Cards(1));
        Card firstCard = cards.get(0);
        Assert.assertEquals("BadOmen", cardConverter.cardDifferentiator(firstCard).getClass().getSimpleName());
    }
}