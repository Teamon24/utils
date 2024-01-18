package home;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssignIfTest {

    /**
    * Test for {@link Assign#assignIf}.
    */
    @Test
    public void assTest() {
        var data = new AssignIfTestData();

        RuntimeException expectedException = new RuntimeException();

        Assertions.assertThrows(
            expectedException.getClass(),
            () -> Assign
                    .of(data.lesser(), (it) -> it > data.greater())
                    .assignIf(data.lesser(), (it) -> it > data.greater())
                    .orElseThrow(() -> expectedException)
        );

        Assertions.assertThrows(
            expectedException.getClass(),
            () -> Assign
                .of(data.greater(), (it) -> it < data.lesser())
                .get()
        );

        Assertions.assertThrows(
            expectedException.getClass(),
            () -> Assign
                .of(data.greater(), (it) -> it < data.lesser())
                .orElseGet(() -> null)
                .get()
        );

        Assertions.assertThrows(expectedException.getClass(), () ->
            Assign
                .of(data.lesser(), (it) -> it > data.greater())
                .assignIf(data.lesser(), (it) -> it > data.greater())
                .orElseGet(() -> null)
                .orElseThrow(() -> expectedException)
        );

        Assertions.assertDoesNotThrow(
            () -> Assign
                .of(5, (it) -> it > 10)
                .assignIf(data.greater(), (it) -> it > data.lesser())
                .orElseThrow(() -> expectedException)
        );

        Assertions.assertDoesNotThrow(
            () -> Assign
                .of(data.greater(), (it) -> it > data.lesser())
                .orElseThrow(() -> expectedException)
        );

        Assertions.assertEquals(
            data.lesser(),
            Assign
                .of(data.greater(), (it) -> it < data.lesser())
                .orElseGet(() -> null)
                .orElse(data.lesser())
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(data.greater(), (it) -> it > data.lesser())
                .get()
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(data.greater(), (it) -> it > data.lesser())
                .orElse(data.incremented())
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIf(1, (it) -> it > 4)
                .orElseGet(() -> null)
                .orElseGet(() -> null)
                .orElseGet(data::greater)
                .orElseGet(() -> null)
                .orElse(data.incremented())
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIf(1, (it) -> it > 4)
                .orElseGet(() -> null)
                .orElseGet(() -> null)
                .assignIf(data.greater(), (it) -> it > data.lesser())
                .orElseGet(() -> null)
                .orElse(data.incremented())
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIf(1, (it) -> it > 4)
                .orElseGet(() -> null)
                .assignIf(data.greater(), (it) -> it > data.lesser())
                .orElse(data.incremented())
        );

        data.set(10);
        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIf(1, (it) -> it > 4)
                .orElseGet(() -> null)
                .assignIf(1, (it) -> it < 0)
                .orElseGet(() -> null)
                .orElse(data.greater())
        );

        data.set(2);
        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIf(1, (it) -> it > 4)
                .orElseGet(() -> null)
                .orElseGet(() -> null)
                .assignIf(1, (it) -> it < 0)
                .orElse(data.greater())
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIf(1, (it) -> it > 4)
                .orElseGet(() -> null)
                .orElseGet(() -> null)
                .assignIf(1, (it) -> it > 0)
                .assignIf(data.greater(), (it) -> it > data.lesser())
                .orElse(data.incremented())
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(9, (it) -> it > 10)
                .assignIfNull(data.greater(), (it) -> it > data.lesser())
                .assignIfNull(1, (it) -> it > 0)
                .assignIfNull(2, (it) -> it > 0)
                .orElse(data.incremented())
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(9, (it) -> it > 10)
                .orElseGet(() -> null)
                .assignIfNull(data.greater(), (it) -> it > data.lesser())
                .assignIfNull(2, (it) -> it > 0)
                .orElse(data.incremented())
        );

        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIfNull(5, (it) -> it > 4)
                .assignIfNull(1, (it) -> it > 0)
                .assignIf(data.greater(), (it) -> it > data.lesser())
                .orElse(data.incremented())
        );


        data.random();
        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIf(data.greater(), (it) -> it > data.lesser())
                .orElse(data.incremented())
        );

        data.random();
        Assertions.assertEquals(
            data.greater(),
            Assign
                .of(5, (it) -> it > 10)
                .assignIf(1, (it) -> it > 4)
                .orElse(data.greater())
        );
    }
}