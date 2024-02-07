package de.bitexpert.javacodyserver.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodyResponse {
	String[] cody;
	String[] details;
	CodyResponseType type = CodyResponseType.INFO;

	/**
	 * Required if this response is a question. This callback will handle the reply based
	 * on the String that the user sent in response to the Cody question and returns a new CodyResponse.
	 */
	@JsonIgnore
	Function<String, CodyResponse> replyCallback;

	public CodyResponse(String cody, String details, CodyResponseType type) {
		this.cody = new String[] {cody};
		this.details = new String[] {details};
		this.type = type;
	}
}
