package midas;

import org.springframework.data.annotation.Id;

public class Ticker {

    @Id
    private String id;

    private String name;

    public Ticker() {}

    public Ticker(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Ticker[id=%s, name='%s']",
                id, name);
    }
}
