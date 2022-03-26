package org.uno.processactions

import groovy.util.logging.Log4j2
import org.uno.Game
import org.uno.SystemInputUtils
import org.uno.cards.CardType
import org.uno.player.RealPlayer
import org.uno.player.actions.IPlayerAction
import org.uno.player.actions.PlayCardAction

@Log4j2
class PlayCardActionProcessor implements IPlayerActionProcessor {

    @Override
    void processAction(Game game, IPlayerAction action) {
        game.with {
            if (((PlayCardAction) action).getCard().getCardType() == CardType.REVERSE) {
                reverseDirection()
            }
            moveActiveCardToPlayed()
            updateGameState((PlayCardAction) action)
        }

        game.printGameState()

        boolean withUno = SystemInputUtils.readLine() == '+U'
        if (game.getActivePlayer().viewCards().size() == 1) {
            if (game.getActivePlayer().class == RealPlayer) {
                handleUnoEventByBots(game, action)
            } else {
                if (game.withRealPlayer()) {
                    handleUnoEventByRealUser(game, action, withUno)
                } else {
                    handleUnoEventByBots(game, action)
                }
            }
        }
    }

    private static handleUnoEventByBots(Game game, IPlayerAction action) {
        if (!((PlayCardAction) action).getWithUno() && new Random().nextInt(100) > 25) {
            log.info("${game.getActivePlayer().getName()} didn't say UNO!")
            game.giveCardsToPlayer(2)
        }
    }

    private static handleUnoEventByRealUser(Game game, IPlayerAction action, boolean withUno) {
        if (!((PlayCardAction) action).getWithUno()) {
            if (withUno || new Random().nextInt(100) > 75) {
                log.info("${game.getActivePlayer().getName()} didn't say UNO!")
                game.giveCardsToPlayer(2)
            }
        }
    }
}
