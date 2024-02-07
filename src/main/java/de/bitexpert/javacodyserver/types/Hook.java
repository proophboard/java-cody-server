package de.bitexpert.javacodyserver.types;

@FunctionalInterface
public interface Hook {
	CodyResponse apply(Node node, CodyConfigContext context);
}
