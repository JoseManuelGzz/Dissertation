package tracks.multiPlayer.opponentModels;

import core.game.StateObservationMulti;
import ontology.Types;
import tools.Utils;
import tracks.multiPlayer.tools.heuristics.SimpleStateHeuristic;

import java.util.Random;

/**
 * Created by jmanu on 6/27/2017.
 */
public class Minimum {

    private int oppID;
    private Types.ACTIONS bestAction;
    private double minQ;

    public Minimum(int oppID) {
        this.oppID = oppID;
    }

    public Types.ACTIONS getOpponentAction(StateObservationMulti stateObs, double epsilon, Random m_rnd) {

        //System.out.println("Minimum");

        this.bestAction = null;
        this.minQ = Double.POSITIVE_INFINITY;

        SimpleStateHeuristic heuristic = new SimpleStateHeuristic(stateObs);

        for (Types.ACTIONS opAction : stateObs.getAvailableActions(this.oppID)) {

            StateObservationMulti stCopy = stateObs.copy();

            stCopy.advance(opAction);

            double Q = heuristic.evaluateState(stCopy, this.oppID);
            Q = Utils.noise(Q, epsilon, m_rnd.nextDouble());

            if (Q < this.minQ) {
                this.minQ = Q;
                this.bestAction = opAction;
            }

        }

        return this.bestAction;
    }

}
