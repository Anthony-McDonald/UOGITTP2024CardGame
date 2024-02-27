package structures.AI;
import actors.GameActor;
import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Avatar;
import structures.basic.MoveableUnit;
import structures.basic.Player;

import java.util.ArrayList;

public class AI extends Player {
	private GameState gameState;
	private ActorRef actorRef;
	private ArrayList<MoveableUnit> allUnits;

	public AI(boolean userOwned, GameState gameState) {
		super(userOwned);
		this.gameState = gameState;

	}

	public void setActorRef(ActorRef out){
		this.actorRef = out;
	}
}
