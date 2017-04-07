package com.basut.townmanager.model;

public class Storage extends Building {
	private int wood;
	private int stone;
	private int food;

	public void setWood(int wood) {
		if (wood < 0) {
			wood = 0;
		}
		this.wood = wood;
	}
	
	public void setFood(int food) {
		if (food < 0) {
			food = 0;
		}
		this.food = food;
	}
	
	public void setStone(int stone) {
		if (stone < 0) {
			stone = 0;
		}
		this.stone = stone;
	}
	
	public int getWood() {
		return wood;
	}
	
	public int getFood() {
		return food;
	}
	
	public int getStone() {
		return stone;
	}

	@Override
	public String toString() {
		return "Storage [wood=" + wood + ", stone=" + stone + ", food=" + food
				+ "]";
	}

	@Override
	public boolean upgrade() {
		return false;
	}

	@Override
	public void calculateOutput(Storage lager) {
		// TODO Auto-generated method stub

	}
	
	
	
}
