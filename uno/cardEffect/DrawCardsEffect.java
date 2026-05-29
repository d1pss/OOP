package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.players.Player;
import uno.cards.Card;

public class DrawCardsEffect implements CardEffect {
    private int amount;

    public DrawCardsEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void execute(EffectContext context, OutputCommand out, int PlayerId, Card cardUsed) {
        // 1. Descobre quem é a vítima (o próximo jogador)
        Player target = context.getReadOnlyPlayersList().get(context.nextPlayerId());

        context.drawCards(target, amount);
        
        context.skipPlayers(1);

        out.outputDrawCard(context.nextPlayerId(), amount);
    }
}