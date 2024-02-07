package de.bitexpert.javacodyserver.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NodeType {
	@JsonProperty("event") EVENT("event"),
	@JsonProperty("command") COMMAND("command"),
	@JsonProperty("role") ROLE("role"),
	@JsonProperty("aggregate") AGGREGATE("aggregate"),
	@JsonProperty("document") DOCUMENT("document"),
	@JsonProperty("policy") POLICY("policy"),
	@JsonProperty("hotSpot") HOTSPOT("hotspot"),
	@JsonProperty("externalSystem") EXTERNAL_SYSTEM("externalSystem"),
	@JsonProperty("ui") UI("ui"),
	@JsonProperty("feature") FEATURE("feature"),
	@JsonProperty("boundedContext") BOUNDED_CONTEXT("boundedContext"),
	@JsonProperty("freeText") FREE_TEXT("freeText"),
	@JsonProperty("textCard") TEXT_CARD("textCard"),
	@JsonProperty("edge") EDGE("edge"),
	@JsonProperty("misc") MISC("misc"),
	@JsonProperty("icon") ICON("icon"),
	@JsonProperty("image") IMAGE("image"),
	@JsonProperty("layer") LAYER("layer");

	private final String name;

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Example: NodeType.BOUNDED_CONTEXT returns "onBoundedContext"
	 */
	public String getHookName() {
		return "on" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
}
