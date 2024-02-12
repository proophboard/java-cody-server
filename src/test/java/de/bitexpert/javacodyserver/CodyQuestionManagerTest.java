package de.bitexpert.javacodyserver;

import de.bitexpert.javacodyserver.types.CodyResponse;
import de.bitexpert.javacodyserver.types.CodyResponseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CodyQuestionManagerTest {

	private CodyQuestionManager codyQuestionManager;

	@BeforeEach
	void setUp() {
		codyQuestionManager = new CodyQuestionManager();
	}

	@Test
	void checkQuestion() {
		CodyResponse infoResponse = new CodyResponse("cody", "details", CodyResponseType.INFO);

		// info response should just be passed through
		assertEquals(infoResponse, codyQuestionManager.checkQuestion(infoResponse));

		CodyResponse question = new CodyResponse("cody", "details", CodyResponseType.QUESTION);
		question.setReplyCallback((reply) -> new CodyResponse());

		// question with callback should also just be passed through
		assertEquals(question, codyQuestionManager.checkQuestion(question));
	}

	@Test
	void checkQuestionAndHandleReply() {
		// handling reply without a question
		assertEquals(CodyResponseType.WARNING, codyQuestionManager.handleReply("yes").getType());

		CodyResponse question = new CodyResponse("Is this working?", "", CodyResponseType.QUESTION);
		question.setReplyCallback((reply) -> new CodyResponse("Good.", "", CodyResponseType.INFO));
		codyQuestionManager.checkQuestion(question);

		// handling reply to a question
		assertEquals(CodyResponseType.INFO, codyQuestionManager.handleReply("yes").getType());
	}

}