package tracks.multiPlayer.advanced.minimumMCTS;

/**
 * Created by jmanu on 7/2/2017.
 */

import core.game.StateObservationMulti;
import core.player.AbstractMultiPlayer;
import ontology.Types;
import tools.ElapsedCpuTimer;
import tracks.multiPlayer.advanced.minimumMCTS.SingleMCTSPlayer;

import java.util.ArrayList;
import java.util.Random;

public class Agent extends AbstractMultiPlayer {

    public int[] NUM_ACTIONS;
    public Types.ACTIONS[][] actions;
    public int id, oppID, no_players;

    protected SingleMCTSPlayer mctsPlayer;

    /**
     * Public constructor with state observation and time due.
     * @param so state observation of the current game.
     * @param elapsedTimer Timer for the controller creation.
     */
    public Agent(StateObservationMulti so, ElapsedCpuTimer elapsedTimer, int playerID)
    {
        //get game information

        no_players = so.getNoPlayers();
        id = playerID;
        oppID = (id + 1) % so.getNoPlayers();

        //Get the actions for all players in a static array.

        NUM_ACTIONS = new int[no_players];
        actions = new Types.ACTIONS[no_players][];
        for (int i = 0; i < no_players; i++) {

            ArrayList<Types.ACTIONS> act = so.getAvailableActions(i);

            actions[i] = new Types.ACTIONS[act.size()];
            for (int j = 0; j < act.size(); ++j) {
                actions[i][j] = act.get(j);
            }
            NUM_ACTIONS[i] = actions[i].length;
        }

        //Create the player.

        mctsPlayer = getPlayer(so, elapsedTimer, NUM_ACTIONS, actions, id, oppID, no_players);
    }

    public SingleMCTSPlayer getPlayer(StateObservationMulti so, ElapsedCpuTimer elapsedTimer, int[] NUM_ACTIONS, Types.ACTIONS[][] actions, int id, int oppID, int no_players) {
        return new SingleMCTSPlayer(new Random(), NUM_ACTIONS, actions, id, oppID, no_players);
    }


    /**
     * Picks an action. This function is called every game step to request an
     * action from the player.
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     * @return An action for the current state
     */
    public Types.ACTIONS act(StateObservationMulti stateObs, ElapsedCpuTimer elapsedTimer) {

        int action = 0;

        //Set the state observation object as the new root of the tree.
        mctsPlayer.init(stateObs);

        do {
            //Determine the action using MCTS...
            action = mctsPlayer.run(elapsedTimer);

            //System.out.println(mctsPlayer.getAdvanceCounter());

        } while(mctsPlayer.getAdvanceCounter() < 900);

        mctsPlayer.resetAdvanceCounter();

        //... and return it.
        return actions[id][action];
    }

}