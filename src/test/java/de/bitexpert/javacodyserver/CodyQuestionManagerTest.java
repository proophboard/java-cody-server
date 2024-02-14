package de.bitexpert.javacodyserver;

import de.bitexpert.javacodyserver.types.CodyResponse;
import de.bitexpert.javacodyserver.types.CodyResponseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodyQuestionManagerTest {

	private CodyQuestionManager codyQuestionManager;

	@BeforeEach
	void setUp() {
		codyQuestionManager = new CodyQuestionManager();
	}

	@Test
	void checkQuestion() {
		CodyResponse infoResponse = new CodyResponse("Info Response", "", CodyResponseType.INFO);

		// info response should just be passed through
		assertEquals(infoResponse, codyQuestionManager.checkQuestion(infoResponse));

		CodyResponse question = new CodyResponse("Question", "", CodyResponseType.QUESTION);
		question.setReplyCallback((reply) -> new CodyResponse());

		// question with callback should also just be passed through
		assertEquals(question, codyQuestionManager.checkQuestion(question));
	}

	@Test
	void handleReply() {
		// handling reply without a question
		assertEquals(CodyResponseType.WARNING, codyQuestionManager.handleReply("yes").getType());

		codyQuestionManager.setCurrentReplyCallback(
			(reply) -> new CodyResponse("Question Callback", "", CodyResponseType.INFO)
		);

		// handling reply to a question
		assertEquals(CodyResponseType.INFO, codyQuestionManager.handleReply("yes").getType());
	}

	@Test
	void getTestQuestion() {
		assertEquals(CodyResponseType.QUESTION, codyQuestionManager.getTestQuestion().getType());
	}

}