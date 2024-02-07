package de.bitexpert.javacodyserver.types;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CodyConfigContext {
	Map<String, Node> syncedNodes;
	String boardId;
	String boardName;
	String userId;
	String userName;
}
