package org.uno.cards

class BaseCard implements ICard {

    protected CardType type

    BaseCard(CardType type) {
        this.type = type
    }

    @Override
    CardType getCardType() {
        type
    }
}
