package tracks.multiPlayer.opponentModels;

/**
 * Created by jmanu on 6/15/2017.
 */

import java.util.Random;

import ontology.Types;
import core.game.StateObservationMulti;
import tracks.multiPlayer.tools.heuristics.SimpleStateHeuristic;
import tools.Utils;


public class Alphabeta
{

    private int oppID;
    private Types.ACTIONS bestAction;
    private double maxQ;

    public Alphabeta(int oppID) {
        this.oppID = oppID;
    }

    public Types.ACTIONS getOpponentAction(StateObservationMulti stateObs, double epsilon, Random m_rnd) {

        System.out.println("Alphabeta");

        this.bestAction = null;
        this.maxQ = Double.NEGATIVE_INFINITY;

        SimpleStateHeuristic heuristic =  new SimpleStateHeuristic(stateObs);

        for(Types.ACTIONS opAction : stateObs.getAvailableActions(oppID)) {

            StateObservationMulti stCopy = stateObs.copy();

            double Q = heuristic.evaluateState(stCopy, oppID);
            Q = Utils.noise(Q, epsilon, m_rnd.nextDouble());

            if (Q > maxQ) {
                maxQ = Q;
                bestAction = opAction;
            }

        }

        return bestAction;
    }
}
