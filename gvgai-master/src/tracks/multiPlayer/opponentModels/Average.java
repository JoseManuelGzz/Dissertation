package tracks.multiPlayer.opponentModels;

import core.game.StateObservationMulti;
import ontology.Types;
import tools.Utils;
import tracks.multiPlayer.tools.heuristics.SimpleStateHeuristic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jmanu on 6/19/2017.
 */

public class Average {
    private int oppID;
    private Types.ACTIONS bestAction;
    private ArrayList<Double> QValues = new ArrayList<Double>();
    private ArrayList<Types.ACTIONS> opponentActions = new ArrayList<Types.ACTIONS>();

    public Average(int oppID) {
        this.oppID = oppID;
    }

    public Types.ACTIONS getOpponentAction(StateObservationMulti stateObs, double epsilon, Random m_rnd) {

        System.out.println("Average");

        this.bestAction = null;
        int cont = 0;
        double avgQ = 0.0;

        SimpleStateHeuristic heuristic =  new SimpleStateHeuristic(stateObs);

        for(Types.ACTIONS opAction : stateObs.getAvailableActions(oppID)) {

            StateObservationMulti stCopy = stateObs.copy();

            stCopy.advance(opAction);

            double Q = heuristic.evaluateState(stCopy, oppID);
            Q = Utils.noise(Q, epsilon, m_rnd.nextDouble());

            this.QValues.add(Q);
            this.opponentActions.add(opAction);

            cont++;
        }

        avgQ = averageQ(this.QValues);

        bestAction = closestAction(avgQ);

        return bestAction;
    }

    public double averageQ(ArrayList<Double> arrQ) {

        double avgQ = 0.0;
        double acum = 0.0;

        for (int i = 0; i < arrQ.size(); i++) {
            acum += arrQ.get(i);
        }

        avgQ = acum/arrQ.size();

        return avgQ;
    }

    public Types.ACTIONS closestAction(double avg) {

        Types.ACTIONS avgAction = null;
        double distance = Math.abs(this.QValues.get(0) - avg);
        double clDistance = 0.0;
        int index = 0;

        for (int i = 0; i < this.opponentActions.size(); i++) {

            clDistance = Math.abs(QValues.get(i) - avg);

            if (clDistance < distance) {
                distance = clDistance;
                index = i;
            }
        }

        return this.opponentActions.get(index);
    }
}
