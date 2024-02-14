package de.bitexpert.javacodyserver.types;

/**
 * A lambda callback that is invoked when a node is edited.
 */
@FunctionalInterface
public interface Hook {
	CodyResponse apply(Node node, CodyConfigContext context);
}
