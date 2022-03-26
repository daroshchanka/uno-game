package org.uno.player

import org.uno.GameState
import org.uno.cards.ICard
import org.uno.player.actions.IPlayerAction

interface IPlayer {

    String getName()

    IPlayerAction doAction(GameState gameState)

    IPlayerAction doAction(GameState gameState, List<ICard> withCards)

    void takeCards(List<ICard> cards)

    List<ICard> viewCards()

}