package com.labyrinth.game.player.ai.strategy;

import com.labyrinth.game.player.PlayerMovement;
import com.labyrinth.game.player.WallMovement;

public abstract class AIStrategyDecorator extends AIStrategy {

	private AIStrategy antecedent;

	public AIStrategyDecorator(	AIStrategy antecedent){
		this.antecedent = antecedent;
	}

	@Override
	public void processSolutions(){
		this.antecedent.processSolutions();
	}
	
	@Override
	public PlayerMovement getBestPlayerMove(int position){
		return this.antecedent.getBestPlayerMove(position);
	}
	
	@Override
	public WallMovement getBestWallMove(int position){
		return this.antecedent.getBestWallMove(position);
	}
	

	@Override
	public String getInfo() {
		return this.antecedent.getInfo();
	}

}
