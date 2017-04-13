package com.basut.townmanager.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendToDungeon {
	private Long dungeonId;
	private List<Long> idleMinionId;
}
