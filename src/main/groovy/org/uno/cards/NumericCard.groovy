package org.uno.cards

class NumericCard extends ColoredCard {

    protected final int number

    NumericCard(CardColor color, int number) {
        super(CardType.NUMERIC, color)
        this.number = number
    }

    @Override
    CardType getCardType() {
        type
    }

    int getNumber() { number }

    @Override
    String toString() {
        "${getColor().toString()}-${getNumber()}"
    }
}
