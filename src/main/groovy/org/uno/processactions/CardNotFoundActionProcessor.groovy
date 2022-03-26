package org.uno.processactions

import groovy.util.logging.Log4j2
import org.uno.Game
import org.uno.cards.CardType
import org.uno.player.actions.IPlayerAction

@Log4j2
class CardNotFoundActionProcessor implements IPlayerActionProcessor {

    @Override
    void processAction(Game game, IPlayerAction ignored) {
        if (game.isActionApplied()) {
            game.processPostAction()
        } else {
            CardType cardType = game.getActiveCard().getCardType()
            if (cardType == CardType.DRAW_2) {
                applyAction(game, 2)
            } else if (cardType == CardType.WILD_DRAW_4) {
                applyAction(game, 4)
            } else if (cardType == CardType.SKIP) {
                applyAction(game, 0)
            } else {
                game.processPostAction()
            }
        }
    }

    protected static applyAction(Game game, int cardsToTake) {
        game.with {
            if (cardsToTake > 0) {
                giveCardsToPlayer(cardsToTake)
            }
            setActionApplied()
            printGameState()
        }
    }

}
