package blackjack.domain;

import java.util.HashMap;
import java.util.Map;

public class Dealer extends Participant {

    private final Map<Result, Integer> results;

    public Dealer() {
        this.results = initResult();
    }

    private Map<Result, Integer> initResult() {
        Map<Result, Integer> results = new HashMap<>();
        for (Result result : Result.values()) {
            results.put(result, 0);
        }
        return results;
    }

    public void setResults(final Result result) {
        results.put(result, results.get(result) + 1);
    }

    public Map<Result, Integer> getResults() {
        return this.results;
    }
}