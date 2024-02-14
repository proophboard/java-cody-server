package de.bitexpert.javacodyserver.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CodyResponseType {
	@JsonProperty("Info") INFO,
	@JsonProperty("Error") ERROR,
	@JsonProperty("Warning") WARNING,
	@JsonProperty("Question") QUESTION,
	@JsonProperty("SyncRequired") SYNC_REQUIRED,
	@JsonProperty("Empty") EMPTY
}
