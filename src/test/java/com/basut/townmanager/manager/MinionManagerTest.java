package com.basut.townmanager.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.basut.townmanager.model.Minion;
import com.basut.townmanager.utility.enums.MinionType;

public class MinionManagerTest {

	@Test
	public void TestDistributeExpToMinion() {

		Minion minion = Minion.builder().level(1).minionType(MinionType.SORCERER).build();
		MinionManager minionManager = new MinionManager();
		minionManager.distibuteExpToMinion(minion, 250);

		assertEquals(2, minion.getLevel());
		assertEquals(100, minion.getExp());
	}
}
