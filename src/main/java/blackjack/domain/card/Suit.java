package blackjack.domain.card;

public enum Suit {
    HEART("하트"),
    DIAMOND("다이아몬드"),
    SPADE("스페이드"),
    CLOVER("클로버");

    private final String value;

    Suit(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
