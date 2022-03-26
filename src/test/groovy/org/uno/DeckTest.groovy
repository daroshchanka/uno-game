package org.uno

import org.testng.annotations.Test

class DeckTest {

    @Test
    void createDeckTest() {
        Deck deck = new Deck()
        assert deck.getSize() == 108
    }
}
