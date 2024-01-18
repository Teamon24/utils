package home;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Assign<T> {
    private T t;

    public Assign(Function<T, Boolean> condition, T newT) {
        assignIfTrue(condition, newT);
    }

    public static <T> Assign<T> of(T newT, Function<T, Boolean> condition) {
        return new Assign<>(condition, newT);
    }

    public Assign<T> assignIfNull(T newT, Function<T, Boolean> condition) {
        if (this.t != null) return this;
        assignIfTrue(condition, newT);
        return this;
    }

    public Assign<T> assignIf(T newT, Function<T, Boolean> condition) {
        assignIfTrue(condition, newT);
        return this;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return Optional.ofNullable(t).orElseThrow(exceptionSupplier);
    }

    public Assign<T> orElseGet(Supplier<? extends T> valueSupplier) {
        T newT = Optional
            .ofNullable(this.t)
            .orElseGet(valueSupplier);

        return new Assign<>(o -> true, newT);
    }

    public <X extends Throwable> T orElse(T defaultT) throws X {
        return Optional.ofNullable(t).orElse(defaultT);
    }

    public <X extends Throwable> T get() throws X {
        if (t == null) throw new RuntimeException("value is null");
        return t;
    }

    private void assignIfTrue(Function<T, Boolean> condition, T newT) {
        if (condition.apply(newT)) {
            this.t = newT;
        }
    }
}
