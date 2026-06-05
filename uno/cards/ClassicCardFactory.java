package uno.cards;

public class ClassicCardFactory implements CardFactory {


    @Override
    public Card createCard(String color, String type) {
        return new ClassicCard(color, type);
    }

}
