package org.uno.processactions

import org.uno.player.actions.PlayerActionType

class PlayerActionProcessorFactory {

    static IPlayerActionProcessor build(PlayerActionType type) {
        if (type == PlayerActionType.PLAY_CARD) {
            return new PlayCardActionProcessor()
        } else if (type == PlayerActionType.CARD_NOT_FOUND) {
            return new CardNotFoundActionProcessor()
        }
        throw new IllegalArgumentException("Unknown action type $type")
    }
}
