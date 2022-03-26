package org.uno.cards

import org.uno.GameState
import org.uno.player.actions.PlayCardAction

class ColoredCard extends BaseCard {

    protected final CardColor color

    ColoredCard(CardType type, CardColor color) {
        super(type)
        this.color = color
    }

    CardColor getColor() {
        color
    }

    @Override
    GameState buildGameState() {
        new GameState(this, color)
    }

    @Override
    PlayCardAction buildPlayCardAction(CardColor ignored) {
        new PlayCardAction(this, color)
    }

    @Override
    String toString() {
        "${getColor()}-${getCardType()}"
    }

}
