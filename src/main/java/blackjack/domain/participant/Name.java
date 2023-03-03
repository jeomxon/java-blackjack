package blackjack.domain.participant;

import java.util.Objects;

public class Name {

    private final String value;

    public Name(final String value) {
        validateLengthOfName(value);
        validateContainSpace(value);
        this.value = value;
    }

    private void validateContainSpace(final String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름에는 공백이 포함될 수 없습니다.");
        }
    }

    private void validateLengthOfName(final String value) {
        if (value.length() < 1 || value.length() > 5) {
            throw new IllegalArgumentException("이름의 길이는 1이상 5이하만 가능합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return value.equals(name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String getValue() {
        return value;
    }
}
