package it.unicam.formula1Game.strategy;

import it.unicam.formula1Game.cell.Coordinate;

/**
 * Represents a move in the game with an associated weight.
 * The weight is used to evaluate the desirability of the move based on specific criteria.
 *
 * @param coordinate the {@link Coordinate} representing the move's position.
 * @param weight     the weight assigned to the move, indicating its desirability.
 */
public record WeightedMove(Coordinate coordinate, double weight) {
}
