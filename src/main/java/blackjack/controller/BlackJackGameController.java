package blackjack.controller;

import java.util.Map;
import java.util.Optional;

import blackjack.domain.game.BlackJackGame;
import blackjack.domain.card.ShufflingMachine;
import blackjack.domain.game.GameResult;
import blackjack.domain.game.ResultType;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import blackjack.domain.participant.Players;
import blackjack.view.InputView;
import blackjack.view.OutputView;

public class BlackJackGameController {

    private static final String YES_COMMAND = "y";
    private static final String NO_COMMAND = "n";
    private static final int DEALER_DRAWING_BOUNDARY = 16;
    private static final int PLAYER_BUST_BOUNDARY = 21;

    private final ShufflingMachine shufflingMachine;

    public BlackJackGameController(final ShufflingMachine shufflingMachine) {
        this.shufflingMachine = shufflingMachine;
    }

    public void run() {
        final BlackJackGame blackJackGame = generateBlackJackGame();
        final Dealer dealer = blackJackGame.getDealer();
        final Players players = blackJackGame.getPlayers();

        final Map<Player, ResultType> playerResult = playBlackJackGame(blackJackGame, dealer, players);
        final GameResult gameResult = new GameResult(playerResult);

        printFinalResult(dealer, players, gameResult);
    }

    private BlackJackGame generateBlackJackGame() {
        Optional<BlackJackGame> blackJackGame;
        do {
            blackJackGame = generateNames();
        } while (blackJackGame.isEmpty());
        return blackJackGame.get();
    }

    private Optional<BlackJackGame> generateNames() {
        try {
            final String inputNames = InputView.readNames();
            return Optional.of(new BlackJackGame(new Dealer(), new Players(inputNames)));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    private Map<Player, ResultType> playBlackJackGame(final BlackJackGame blackJackGame, final Dealer dealer,
                                                      final Players players) {
        blackJackGame.handOutCards(shufflingMachine);

        OutputView.printInitCard(players.getPlayers(), dealer.getFirstCard());
        handOutCardToPlayers(blackJackGame, players);
        handOutCardToDealer(blackJackGame, dealer);

        return blackJackGame.makePlayerResult();
    }

    private void printFinalResult(final Dealer dealer, final Players players, final GameResult gameResult) {
        OutputView.printCardsWithSum(players.getPlayers(), dealer);
        OutputView.printFinalResult(gameResult.getPlayerResult(), gameResult.findDealerResult());
    }

    private void handOutCardToPlayers(final BlackJackGame blackJackGame, final Players players) {
        for (final Player player : players.getPlayers()) {
            handOutCardToEachPlayer(blackJackGame, player);
        }
    }

    private void handOutCardToEachPlayer(final BlackJackGame blackJackGame, final Player player) {
        boolean command = true;
        while (player.isUnderThanBoundary(PLAYER_BUST_BOUNDARY) && command) {
            final String playerAnswer = inputGameCommandToGetOneMoreCard(player);
            command = isCardHandedOutByCommand(blackJackGame, player, playerAnswer);
        }
    }

    private String inputGameCommandToGetOneMoreCard(final Player player) {
        Optional<String> gameCommand;
        do {
            gameCommand = checkGameCommand(player);
        } while (gameCommand.isEmpty());
        return gameCommand.get();
    }

    private Optional<String> checkGameCommand(final Player player) {
        try {
            final String gameCommand = InputView.readGameCommandToGetOneMoreCard(player.getName());
            validateCorrectCommand(gameCommand);
            return Optional.of(gameCommand);
        } catch (final IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    private boolean isCardHandedOutByCommand(final BlackJackGame blackJackGame, final Player player,
                                             final String playerAnswer) {
        if (playerAnswer.equals(YES_COMMAND)) {
            blackJackGame.hit(shufflingMachine, player);
            OutputView.printParticipantCards(player.getName(), player.getCards());
        }
        OutputView.printParticipantCards(player.getName(), player.getCards());
        return false;
    }

    private void handOutCardToDealer(final BlackJackGame blackJackGame, final Dealer dealer) {
        while (dealer.isUnderThanBoundary(DEALER_DRAWING_BOUNDARY)) {
            blackJackGame.hit(shufflingMachine, dealer);
            OutputView.printDealerReceiveOneMoreCard();
        }
    }

    private void validateCorrectCommand(final String gameCommand) {
        if (!(YES_COMMAND.equals(gameCommand) || NO_COMMAND.equals(gameCommand))) {
            throw new IllegalArgumentException("y 또는 n만 입력 가능합니다.");
        }
    }
}
