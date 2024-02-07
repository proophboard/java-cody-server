package de.bitexpert.javacodyserver;

import de.bitexpert.javacodyserver.types.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class CodyConfig {
	CodyConfigContext context = new CodyConfigContext();
	Map<String, Hook> hooks = new HashMap<>();

	CodyConfig() {
		// add your hooks here
		addHook("onCommand", (Node, CodyConfigContext) -> {
			System.out.println("onCommand");
			return new CodyResponse("Command hook executed", "All good", CodyResponseType.Info);
		});
	}

	/**
	 * Maps a hook name to a hook function.
	 */
	public void addHook(String name, Hook hook) {
		hooks.put(name, hook);
	}
}
