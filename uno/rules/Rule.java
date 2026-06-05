package uno.rules;

import uno.cards.*;
import uno.cardEffect.*;

/**
 * Defines the core ruleset contract for the UNO game.
 * Implementing classes dictate the fundamental game logic, including 
 * player capacity validation, legal move verification, and the resolution 
 * of card effects.
 */
public interface Rule {

    /**
     * Validates whether the specified number of players is supported by this ruleset.
     *
     * @param numberOfPlayers The total number of players attempting to start the game.
     * @return {@code true} if the number of players is valid; {@code false} otherwise.
     */
    public boolean isNumberOfPlayersValid(int numberOfPlayers);

    /**
     * Determines if a specific card can be legally played on top of the current discard pile.
     *
     * @param topCard      The card currently on the top of the discard pile.
     * @param playedCard   The card the player is attempting to play.
     * @param currentColor The current active color of the game (crucial for evaluating moves 
     *                     after a Wild card has been played).
     * @return {@code true} if the move is legal according to the rules; {@code false} otherwise.
     */
    public boolean isPlayable(Card topCard, Card playedCard, String currentColor);

    /**
     * Retrieves the executable effect associated with a specific card.
     *
     * @param playedCard The {@link Card} that was just played.
     * @return The {@link CardEffect} that should be executed by the game engine.
     */
    public CardEffect getEffectOf(Card playedCard);
}