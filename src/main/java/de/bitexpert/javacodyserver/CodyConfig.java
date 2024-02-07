package de.bitexpert.javacodyserver;

import de.bitexpert.javacodyserver.types.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
@Component
public class CodyConfig {
	CodyConfigContext context = new CodyConfigContext();
	Map<String, Hook> hooks = new HashMap<>();

	CodyConfig() {
		// add your hooks here!

		// "onCommand" example
		addHook("onCommand", (Node, CodyConfigContext) -> {
			log.info("onCommand");
			return new CodyResponse("onCommand hook executed.", "It does nothing yet, though.", CodyResponseType.INFO);
		});
	}

	/**
	 * Maps a hook name to a hook function.
	 */
	public void addHook(String name, Hook hook) {
		hooks.put(name, hook);
	}
}
