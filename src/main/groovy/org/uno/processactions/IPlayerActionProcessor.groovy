package org.uno.processactions

import org.uno.Game
import org.uno.player.actions.IPlayerAction

interface IPlayerActionProcessor {

    void processAction(Game game, IPlayerAction action)

}
