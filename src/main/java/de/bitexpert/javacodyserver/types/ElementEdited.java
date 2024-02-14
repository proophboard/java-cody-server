package de.bitexpert.javacodyserver.types;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElementEdited {
	Node node;
	ElementEditedContext context;
}
