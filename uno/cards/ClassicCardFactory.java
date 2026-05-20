package uno.cards;

public class ClassicCardFactory implements CardFactory {

    @Override
    public Card createCard(char color, String type) {
        return new ClassicCard(color, type);
    }

}
