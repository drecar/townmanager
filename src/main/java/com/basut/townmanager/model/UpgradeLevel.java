package com.basut.townmanager.model;

public enum UpgradeLevel {
	NOT_BUILT(0), VERY_LOW(1), LOW(2), MIDDLE(3), HIGH(4), MAX(5);
	private static UpgradeLevel[] levels = values();
	private int levelValue;

	UpgradeLevel(int level) {
		levelValue = level;
	}

	public int getLevelValue() {
		return levelValue;
	}

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

}
