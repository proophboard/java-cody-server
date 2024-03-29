package de.bitexpert.javacodyserver;

import de.bitexpert.javacodyserver.types.CodyResponse;
import de.bitexpert.javacodyserver.types.CodyResponseType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
public class CodyQuestionManager {

	@Setter
	private Function<String, CodyResponse> currentReplyCallback;

	/**
	 * Check if the given CodyResponse is a question and if yes, store its reply callback for later.
	 */
	public CodyResponse checkQuestion(CodyResponse codyResponse) {

		if (codyResponse.getType().equals(CodyResponseType.QUESTION) &&
			codyResponse.getReplyCallback() != null) {
			currentReplyCallback = codyResponse.getReplyCallback();
		}

		return codyResponse;
	}

	/**
	 * If there is an open question, handle the user's reply. Otherwise, confusion.
	 */
	public CodyResponse handleReply(String reply) {
		if (currentReplyCallback != null) {
			CodyResponse response = currentReplyCallback.apply(reply);
			currentReplyCallback = null;
			return response;
		}

		return new CodyResponse(
			"Sorry, not sure what to say.",
			"Did I ask anything?",
			CodyResponseType.WARNING
		);
	}

	/**
	 * This Cody response is used to test the question/reply functionality.
	 */
	public CodyResponse getTestQuestion() {
		Function<String, CodyResponse> replyCallback = (reply) -> {
			log.info("Replied with: {}", reply);

			return new CodyResponse(
					reply.equals("no") ? "Oh ok, maybe I can convince you that bots are awesome." : "Cool! I like you, too",
					reply.equals("no") ? ":tears:" : ":cody_dance:",
					CodyResponseType.INFO
			);
		};

		return new CodyResponse(
			new String[]{"Do you like bots?"},
			new String[]{"Answer with: Yes|no"},
			CodyResponseType.QUESTION,
			replyCallback
		);
	}
}
