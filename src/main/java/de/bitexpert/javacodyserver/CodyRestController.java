package de.bitexpert.javacodyserver;

import de.bitexpert.javacodyserver.general.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class CodyRestController {
	private final QuestionManager questionManager;

	@Autowired
	public CodyRestController(QuestionManager questionManager) {
		this.questionManager = questionManager;
	}

	@PostMapping("/IioSaidHello")
	public CodyResponse iioSaidHello(@RequestBody IioSaidHello body) {
		System.out.print("IioSaidHello");

		return new CodyResponse(
			new String[]{String.format("Hey %s, Cody here. Before we can start, I need to sync the board. This might take a moment.", body.getUser())},
			new String[]{"If you need guidance just ask me with: %c/help", "background-color: rgba(251, 159, 75, 0.2)"},
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
		// TODO
		return new CodyResponse(
			"",
			"",
			CodyResponseType.Info
		);
	}

	@PostMapping("/ConfirmTest")
	public CodyResponse confirmTest() {
		System.out.print("ConfirmTest");

		return questionManager.checkQuestion(questionManager.getTestQuestion());
	}
}
