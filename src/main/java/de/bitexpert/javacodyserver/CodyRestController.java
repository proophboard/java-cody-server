package de.bitexpert.javacodyserver;

import de.bitexpert.javacodyserver.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class CodyRestController {
	private final CodyConfig          config;
	private final CodyQuestionManager codyQuestionManager;

	@Autowired
	public CodyRestController(CodyConfig config, CodyQuestionManager codyQuestionManager) {
		this.config = config;
		this.codyQuestionManager = codyQuestionManager;
	}

	/**
	 * Connects the prooph board with this server.
	 */
	@PostMapping("/IioSaidHello")
	public CodyResponse iioSaidHello(@RequestBody IioSaidHello body) {
		System.out.println("IioSaidHello");

		return new CodyResponse(
			new String[]{String.format("Hey %s, Cody here. Before we can start, I need to sync the board. This might take a moment.", body.getUser())},
			new String[]{"If you need guidance just ask me with: /help", "background-color: rgba(251, 159, 75, 0.2)"},
			CodyResponseType.INFO,
			null
		);
	}

	/**
	 * Handles a user reply to a previous Cody response question.
	 */
	@PostMapping("/UserReplied")
	public CodyResponse userReplied(@RequestBody Reply body) {
		System.out.println("UserReplied");

		return codyQuestionManager.checkQuestion(codyQuestionManager.handleReply(body.getReply()));
	}

	/**
	 * Called when a user triggers Cody on elements on the prooph board, once for each element.
	 */
	@PostMapping("/ElementEdited")
	public CodyResponse elementEdited(@RequestBody ElementEdited body) {
		System.out.println("elementEdited");

		// update persistent context
		if (body.getContext() != null) {
			config.getContext().setBoardId(body.getContext().getBoardId());
			config.getContext().setBoardName(body.getContext().getBoardName());
			config.getContext().setUserId(body.getContext().getUserId());
		}

		// TODO: do optional sync here

		// handle edited element
		String hookName = body.getNode().getType().getHookName();

		if (config.getHooks().containsKey(hookName)) {
			return config.getHooks().get(hookName).apply(body.getNode(), config.getContext());
		}
		else {
			return new CodyResponse(
				new String[]{
					String.format("I'm skipping \"%s\". It's a \"%s\", but I cannot find a hook for it.",
				   		body.getNode().getName(), body.getNode().getType()),
					"color: #fb9f4b; font-weight: bold"
				},
				new String[]{
					String.format("If you want me to handle \"%s\", add a \"%s\" hook to CodyConfig.",
					  	body.getNode().getName(), hookName),
					"color: #414141', 'background-color: rgba(251, 159, 75, 0.2)"
				},
				CodyResponseType.INFO,
				null
			);
		}
	}

	/**
	 * Used as a health check to check the question/reply functionality.
	 */
	@PostMapping("/ConfirmTest")
	public CodyResponse confirmTest() {
		System.out.println("ConfirmTest");

		return codyQuestionManager.checkQuestion(codyQuestionManager.getTestQuestion());
	}
}
