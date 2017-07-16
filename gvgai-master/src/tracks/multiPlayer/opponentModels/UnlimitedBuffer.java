package tracks.multiPlayer.opponentModels;

import ontology.Types;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jmanu on 7/10/2017.
 */
public class UnlimitedBuffer {

    private int oppID;
    private Types.ACTIONS bestAction;
    private int actionIndex;
    private int rnd;
    // An array with the actions being up, down, left, right, nil, and use respectively
    private Types.ACTIONS [] actionType = new Types.ACTIONS [] {Types.ACTIONS.ACTION_UP, Types.ACTIONS.ACTION_DOWN,
            Types.ACTIONS.ACTION_LEFT, Types.ACTIONS.ACTION_RIGHT, Types.ACTIONS.ACTION_NIL, Types.ACTIONS.ACTION_USE};
    private double [] discreteProbabilities = new double [] {0.1415, 0.1447, 0.2022, 0.1962, 0.2036, 0.1115};
    public ArrayList<Types.ACTIONS> buffer = new ArrayList<Types.ACTIONS>();

    public UnlimitedBuffer(int oppID) {
        this.oppID = oppID;
    }

    public Types.ACTIONS getOpponentAction(ArrayList <Types.ACTIONS> buffer) {

        //System.out.println("UnlimitedBuffer");

        this.bestAction = Types.ACTIONS.ACTION_LEFT;

        if(buffer.size() < 20) {
            //System.out.println("IF");
            ArrayList<Integer> probActionGenerated = new ArrayList<Integer>();

            for (int i = 0; i < discreteProbabilities.length; i++) {
                for (int j = 0; j < discreteProbabilities[i] * 10000; j++) {
                    probActionGenerated.add(i);
                }
            }

            // Roll a random number between 0 and 10
            this.rnd = new Random().nextInt(probActionGenerated.size());
            this.actionIndex = probActionGenerated.get(rnd);
            this.bestAction = actionType[actionIndex];
        }
        else {

            this.rnd = new Random().nextInt(buffer.size());
            this.bestAction = buffer.get(rnd);

        }

        return this.bestAction;
    }

}
