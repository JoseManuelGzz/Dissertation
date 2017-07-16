package tracks.multiPlayer.opponentModels;

import core.game.StateObservationMulti;
import ontology.Types;
import tools.Utils;
import tracks.multiPlayer.tools.heuristics.SimpleStateHeuristic;

import java.util.Random;

/**
 * Created by jmanu on 6/20/2017.
 */
public class SameAction {

    private int oppID;
    private Types.ACTIONS bestAction;

    public SameAction(int oppID) {
        this.oppID = oppID;
    }

    public Types.ACTIONS getOpponentAction(Types.ACTIONS action) {

        //System.out.println("SameAction");
        this.bestAction = action;

        return this.bestAction;
    }

}
