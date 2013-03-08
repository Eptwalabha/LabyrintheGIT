package com.labyrinth.game.player.ai.strategy;

public class AIMediumStrategy extends AIStrategyDecorator{

	public AIMediumStrategy(AIStrategy antecedent) {
		super(antecedent);
	}

	@Override
	public void processSolutions(){
		super.processSolutions();
		System.out.println("medium");
	}
		
	@Override
	public String getInfo() {
		return super.getInfo() + "\nMedium";
	}

}
