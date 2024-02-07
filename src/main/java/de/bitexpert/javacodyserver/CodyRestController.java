package de.bitexpert.javacodyserver;

import de.bitexpert.javacodyserver.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class CodyRestController {
	private final CodyConfig config;
	private final QuestionManager questionManager;

	@Autowired
	public CodyRestController(CodyConfig config, QuestionManager questionManager) {
		this.config = config;
		this.questionManager = questionManager;
	}

	@PostMapping("/IioSaidHello")
	public CodyResponse iioSaidHello(@RequestBody IioSaidHello body) {
		System.out.print("IioSaidHello");

		return new CodyResponse(
			new String[]{String.format("Hey %s, Cody here. Before we can start, I need to sync the board. This might take a moment.", body.getUser())},
			new String[]{"If you need guidance just ask me with: /help", "background-color: rgba(251, 159, 75, 0.2)"},
			CodyResponseType.Info,
			null
		);
	}

	@PostMapping("/UserReplied")
	public CodyResponse userReplied(@RequestBody Reply body) {
		System.out.print("UserReplied");

		return questionManager.checkQuestion(questionManager.handleReply(body.getReply()));
	}

	@PostMapping("/ElementEdited")
	public CodyResponse elementEdited(@RequestBody ElementEdited body) {
		System.out.print("elementEdited");

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
				CodyResponseType.Info,
				null
			);
		}
	}

	@PostMapping("/ConfirmTest")
	public CodyResponse confirmTest() {
		System.out.print("ConfirmTest");

		return questionManager.checkQuestion(questionManager.getTestQuestion());
	}
}
