package tracks.multiPlayer.opponentModels;

import core.game.StateObservationMulti;
import ontology.Types;
import tools.Utils;
import tracks.multiPlayer.tools.heuristics.SimpleStateHeuristic;

import java.util.Random;

/**
 * Created by jmanu on 6/20/2017.
 */
public class Mirror {

    private int oppID;
    private int bestAction;

    public Mirror(int oppID, int bestAction) {
        this.oppID = oppID;
        this.bestAction = bestAction;
    }

    public Types.ACTIONS getOpponentAction(Types.ACTIONS action) {

        Types.ACTIONS mirroredAction = null;

        //System.out.println("Mirror");

        if (action == Types.ACTIONS.ACTION_DOWN) {
            mirroredAction = Types.ACTIONS.ACTION_UP;
        }
        else if (action == Types.ACTIONS.ACTION_UP) {
            mirroredAction = Types.ACTIONS.ACTION_DOWN;
        }
        else if (action == Types.ACTIONS.ACTION_LEFT) {
            mirroredAction = Types.ACTIONS.ACTION_RIGHT;
        }
        else if (action == Types.ACTIONS.ACTION_RIGHT) {
            mirroredAction = Types.ACTIONS.ACTION_LEFT;
        }
        else {
            mirroredAction = action;;
        }

        return mirroredAction;
    }
}
