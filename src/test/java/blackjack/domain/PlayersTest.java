package blackjack.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PlayersTest {

    @DisplayName("문자열을 받아 Player를 생성한다.")
    @Test
    void createPlayersWhenGivenString() {
        // given
        String input = "pobi,jason";

        // when
        Players players = new Players(input);

        // then
        assertThat(players.getPlayers().size()).isEqualTo(2);
    }

    @DisplayName("플레이어의 수가 1명 이상 7명 이하가 아니면 예외를 던진다.")
    @Test
    void validateNumberOfPlayers() {
        // given
        String input = "a,b,c,d,e,f,g,h";

        // when & then
        assertThatThrownBy(() -> new Players(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("플레이어의 수는 1명 이상 7명 이하만 가능합나다.");
    }
}
