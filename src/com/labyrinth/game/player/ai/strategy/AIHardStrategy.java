package com.labyrinth.game.player.ai.strategy;

public class AIHardStrategy extends AIStrategyDecorator {

	public AIHardStrategy(AIStrategy antecedent) {
		super(antecedent);
	}

	@Override
	public void processSolutions(){
		super.processSolutions();
		System.out.println("hard");
		
	}

	@Override
	public String getInfo() {
		return super.getInfo() + "\nHard";
	}

}
