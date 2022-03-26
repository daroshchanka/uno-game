package org.uno.cards

import org.uno.GameState
import org.uno.player.actions.PlayCardAction

interface ICard {

    CardType getCardType()

    default String toString() {
        "${getCardType()}"
    }

    default GameState buildGameState() {
        new GameState(this)
    }

    default PlayCardAction buildPlayCardAction(CardColor optionalColor) {
        new PlayCardAction(this, optionalColor)
    }

}