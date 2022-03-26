package org.uno.player

import groovy.util.logging.Log4j2
import org.uno.GameState
import org.uno.cards.CardColor
import org.uno.cards.CardType
import org.uno.cards.ICard
import org.uno.cards.matcher.CardsMatchUtils
import org.uno.player.actions.BasePlayerAction
import org.uno.player.actions.IPlayerAction
import org.uno.player.actions.PlayCardAction
import org.uno.player.actions.PlayerActionType
import org.uno.player.strategies.simple.ChooseCardSimpleStrategyFactory

@Log4j2
class BotPlayer implements IPlayer {

    protected List<ICard> cards
    final String name

    BotPlayer(String name) {
        this.name = name
        cards = []
    }

    @Override
    List<ICard> viewCards() { cards }

    @Override
    void takeCards(List<ICard> toAdd) {
        log.info("[$name] take +${toAdd.size()} card(s)")
        cards.addAll(toAdd)
    }

    @Override
    IPlayerAction doAction(GameState gameState, List<ICard> fromCards = cards) {
        if (gameState.isChooseColorRequired()) {
            gameState.setActiveColor(chooseColor())
        }
        ICard card = chooseCard(gameState, fromCards)
        playCard(card)
    }

    protected void tryToSayUno(PlayCardAction action) {
        // default, just 70% probability of success
        if (1 + (new Random().nextInt(100)) > 30) {
            action.setWithUno(true)
        }
    }

    protected IPlayerAction playCard(ICard cardToPlay) {
        if (cardToPlay) {
            cards.remove(cardToPlay)
            PlayCardAction action = cardToPlay.buildPlayCardAction(
                    cardToPlay.getCardType() in [CardType.WILD, CardType.WILD_DRAW_4] ? chooseColor() : null
            )
            if (cards.size() == 1) {
                tryToSayUno(action)
            }
            action
        } else {
            log.debug("Cards not found.. Player cards ${viewCards()}")
            new BasePlayerAction(PlayerActionType.CARD_NOT_FOUND)
        }
    }

    protected ICard chooseCard(GameState gameState, List<ICard> fromCards) {
        ChooseCardSimpleStrategyFactory.build(gameState).chooseCard(fromCards)
    }

    protected CardColor chooseColor() {
        CardColor.values().toList().max { CardColor color ->
            cards.findAll { CardsMatchUtils.matchByColor(it, color) }.size()
        }
    }

}
