package home;

import java.util.Random;

class AssignIfTestData {
    private int lesser = 10;
    private int expected = new Random().nextInt(lesser, Integer.MAX_VALUE);

    int lesser() {
        return lesser;
    }

    int greater() {
        return expected;
    }

    int set(Integer expected) {
        this.expected = expected;
        this.lesser = expected - 1;
        return this.expected;
    }

    int incremented() {
        return this.expected + 1;
    }

    int random() {
        return set(new Random().nextInt(this.expected, Integer.MAX_VALUE));
    }
}
