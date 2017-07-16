package tracks.multiPlayer.advanced.unlimitedbufferMCTS;


import core.game.StateObservationMulti;
import ontology.Types;
import tools.ElapsedCpuTimer;
import tracks.multiPlayer.advanced.unlimitedbufferMCTS.SingleTreeNode;

import java.util.Random;

public class SingleMCTSPlayer
{


    /**
     * Root of the tree.
     */
    public SingleTreeNode m_root;

    int[] NUM_ACTIONS;
    Types.ACTIONS[][] actions;

    /**
     * Random generator.
     */
    public Random m_rnd;
    public int id, oppID, no_players;
    public int advanceCounter = 0;


    public SingleMCTSPlayer(Random a_rnd, int[] NUM_ACTIONS, Types.ACTIONS[][] actions, int id, int oppID, int no_players)
    {
        m_rnd = a_rnd;
        this.NUM_ACTIONS = NUM_ACTIONS;
        this.actions = actions;
        this.id = id;
        this.oppID = oppID;
        this.no_players = no_players;
    }

    /**
     * Inits the tree with the new observation state in the root.
     * @param a_gameState current state of the game.
     */
    public void init(StateObservationMulti a_gameState)
    {
        //Set the game observation to a newly root node.
        //System.out.println("learning_style = " + learning_style);
        m_root = new SingleTreeNode(m_rnd, NUM_ACTIONS, actions, id, oppID, no_players);
        m_root.rootState = a_gameState;
    }

    /**
     * Runs MCTS to decide the action to take. It does not reset the tree.
     * @param elapsedTimer Timer when the action returned is due.
     * @return the action to execute in the game.
     */
    public int run(ElapsedCpuTimer elapsedTimer)
    {
        //Do the search within the available time.
        //PASS THE COUNTER FROM AGENT
        m_root.mctsSearch(elapsedTimer);

        //Determine the best action to take and return it.
        int action = m_root.mostVisitedAction();
        //int action = m_root.bestAction();

        this.advanceCounter += m_root.getAdvanceCounter();

        return action;
    }

    public int getAdvanceCounter() {
        return this.advanceCounter;
    }

    public void resetAdvanceCounter() {
        if (this.advanceCounter >= 900) {
            this.advanceCounter = 0;
            this.m_root.advanceCounter = 0;
        }
    }

}