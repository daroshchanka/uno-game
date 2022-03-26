package org.uno.player.actions


import org.uno.cards.CardColor
import org.uno.cards.ICard

class PlayCardAction implements IPlayerAction {

    protected final ICard card
    protected final CardColor color
    private boolean withUno

    PlayCardAction(ICard card, CardColor color) {
        this.card = card
        this.color = color
    }

    ICard getCard() { card }

    CardColor getColor() { color }

    @Override
    PlayerActionType getType() {
        PlayerActionType.PLAY_CARD
    }

    void setWithUno(boolean withUno) {
        this.withUno = withUno
    }

    boolean getWithUno() {
        withUno
    }

    @Override
    String toString() {
        "${type}${withUno ? " UNO!" : ''}"
    }
}
