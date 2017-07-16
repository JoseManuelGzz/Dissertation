package tracks.multiPlayer.opponentModels;

import core.game.StateObservationMulti;
import ontology.Types;
import tools.Utils;
import tracks.multiPlayer.tools.heuristics.SimpleStateHeuristic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jmanu on 7/2/2017.
 */
public class Probabilistic
{

    private int oppID;
    private Types.ACTIONS bestAction;
    private int actionIndex;
    private int rnd;
    // An array with the actions being up, down, left, right, nil, and use respectively
    private int [] actionGenerated = new int [] {1, 2, 3, 4, 5, 6};
    private double [] discreteProbabilities = new double [] {0.1415, 0.1447, 0.2022, 0.1962, 0.2036, 0.1115};

    public Probabilistic(int oppID) {
        this.oppID = oppID;
    }

    public Types.ACTIONS getOpponentAction(StateObservationMulti stateObs, double epsilon, Random m_rnd) {

        //System.out.println("Probabilistic");

        this.bestAction = Types.ACTIONS.ACTION_DOWN;
        ArrayList<Integer> probActionGenerated = new ArrayList<Integer>();

        for (int i = 0; i < discreteProbabilities.length; i++) {
            for (int j = 0; j < discreteProbabilities[i]*10000; j++) {
                probActionGenerated.add(i+1);
            }
        }

        // Roll a random number between 0 and 10
        this.rnd = new Random().nextInt(probActionGenerated.size());
        this.actionIndex = probActionGenerated.get(rnd);

        if (actionIndex == 1) {
            this.bestAction = Types.ACTIONS.ACTION_UP;
        }
        else if (actionIndex == 2) {
            this.bestAction = Types.ACTIONS.ACTION_DOWN;
        }
        else if (actionIndex == 3) {
            this.bestAction = Types.ACTIONS.ACTION_LEFT;
        }
        else if (actionIndex == 4) {
            this.bestAction = Types.ACTIONS.ACTION_RIGHT;
        }
        else if (actionIndex == 5) {
            this.bestAction = Types.ACTIONS.ACTION_NIL;
        }
        else if (actionIndex == 6) {
            this.bestAction = Types.ACTIONS.ACTION_USE;
        }

        return this.bestAction;
    }
}
