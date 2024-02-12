package de.bitexpert.javacodyserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bitexpert.javacodyserver.types.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class CodyRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private CodyResponse performPost(String url, Object body) throws Exception {
		MvcResult result = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(body)))
				.andExpect(status().isOk())
				.andReturn();

		String resultString = result.getResponse().getContentAsString();
		return new ObjectMapper().readValue(resultString, CodyResponse.class);
	}

	@Test
	@SneakyThrows
	void handshake() {
		IioSaidHello body = new IioSaidHello("MyName");
		CodyResponse codyResponse = performPost("/messages/IioSaidHello", body);
		assertEquals(CodyResponseType.INFO, codyResponse.getType());
	}

	@Test
	@SneakyThrows
	void userRepliedWithoutQuestion() {
		Reply body = new Reply("yes");
		CodyResponse codyResponse = performPost("/messages/UserReplied", body);

		// warning, because there was no question for the given reply
		assertEquals(CodyResponseType.WARNING, codyResponse.getType());
	}

	@Test
	@SneakyThrows
	void userRepliedToQuestion() {
		// triggering a question from Cody
		performPost("/messages/ConfirmTest", null);

		// replying to the question gives an info response
		Reply body = new Reply("yes");
		CodyResponse codyResponse = performPost("/messages/UserReplied", body);
		assertEquals(CodyResponseType.INFO, codyResponse.getType());
	}

	@Test
	@SneakyThrows
	void userEditedElement() {
		ElementEdited body = new ElementEdited();
		Node node = new Node();
		node.setName("MyNode");
		node.setType(NodeType.EVENT);
		body.setNode(node);

		// editing an event will return an info response by default,
		// unless there's a hook implemented that does something else
		CodyResponse codyResponse = performPost("/messages/ElementEdited", body);
		assertEquals(CodyResponseType.INFO, codyResponse.getType());
	}

}