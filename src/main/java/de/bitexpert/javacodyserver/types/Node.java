package de.bitexpert.javacodyserver.types;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Node {
	String id;
	String name;
	String description;
	NodeType type;
	String link;
	String[] tags;
	boolean layer;
	boolean defaultLayer;
	Node parent;
	Node[] childrenList;
	Node[] sourcesList;
	Node[] targetsList;
	GraphPoint geometry;
	String metadata;
}
