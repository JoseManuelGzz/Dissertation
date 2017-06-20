package tracks.multiPlayer.opponentModels;

/**
 * Created by jmanu on 6/19/2017.
 */


import java.util.Random;

import ontology.Types;
import core.game.StateObservationMulti;
import tracks.multiPlayer.tools.heuristics.SimpleStateHeuristic;
import tools.Utils;

public class Fallible {

    private int oppID;
    private int rnd;
    private Types.ACTIONS worstAction;
    private Types.ACTIONS bestAction;
    private double minQ;
    private double maxQ;
    private double threshold;

    public Fallible(int oppID) {
        this.oppID = oppID;
        this.threshold = 8;
    }

    public Types.ACTIONS getOpponentAction(StateObservationMulti stateObs, double epsilon, Random m_rnd) {

        System.out.println("Fallible");

        this.worstAction = null;
        this.bestAction = null;
        this.minQ = Double.POSITIVE_INFINITY;
        this.maxQ = Double.NEGATIVE_INFINITY;

        SimpleStateHeuristic heuristic =  new SimpleStateHeuristic(stateObs);

        for(Types.ACTIONS opAction : stateObs.getAvailableActions(oppID)) {

            StateObservationMulti stCopy = stateObs.copy();

            double Q = heuristic.evaluateState(stCopy, oppID);
            Q = Utils.noise(Q, epsilon, m_rnd.nextDouble());

            //System.out.println(opAction + " Q: " + Q);

            if (Q < minQ) {
                minQ = Q;
                worstAction = opAction;
            }

            if (Q > maxQ) {
                maxQ = Q;
                bestAction = opAction;
            }

        }
        //System.out.println("Best Action: " + bestAction);
        //System.out.println("Worst Action: " + worstAction);

        // Roll a random number between 0 and 10
        rnd = new Random().nextInt(10);

        // Return the best action with a big margin
        if (rnd <= threshold) {
            return bestAction;
        }

        // Return the worst action occasionally
        else {
            return worstAction;
        }

    }

}
