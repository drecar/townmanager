package com.basut.townmanager.utility.enums;


public enum UpgradeLevel {
	NOT_BUILT, VERY_VERY_LOW, VERY_LOW, LOW, MEDIUM, MIDDLE, HIGH, HIGHER, VERY_HIGH, MAX;
	private static UpgradeLevel[] levels = values();
	private int levelValue;

	public static UpgradeLevel levelSelect(int level) {
		for (int i = 0; i < levels.length; i++) {
			if (levels[i].levelValue == level) {
				return levels[i];
			}
		}
		return MAX;
	}

	public UpgradeLevel nextLevel() {
		if (this.equals(MAX)) {
			return MAX;
		}
		return levels[(this.ordinal() + 1 % levels.length)];
	}
	
	public int getLevelValue() {
		return this.ordinal();
	}

}
