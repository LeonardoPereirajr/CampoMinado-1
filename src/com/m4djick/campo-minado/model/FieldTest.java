package model;

import exception.ExplosionException;
import model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FieldTest

{
    //runs this method for every method ;)
    int line = 3;
    int column = 3;

    Field field = new Field(line,column);


    @Test
    void testNeighbourUp() {

        Field neighbour = new Field(line-1, column);

        boolean result = field.addNeighbour(neighbour);

        assertTrue(result);
    }

    @Test
    void testNeighbourDown() {

        Field neighbour = new Field(line+1, column);

        boolean result = field.addNeighbour(neighbour);

        assertTrue(result);

    }

    @Test
    void testNeighbourRight() {

        Field neighbour = new Field(line, column+1);

        boolean result = field.addNeighbour(neighbour);

        assertTrue(result);

    }

    @Test
    void testNeighbourLeft() {

        Field neighbour = new Field(line, column-1);

        boolean result = field.addNeighbour(neighbour);

        assertTrue(result);

    }

    @Test
    void testMark() {
        assertFalse(field.isMarked());
    }

    @Test
    void testAlterMark() {
        field.alterMark();
        assertTrue(field.isMarked());
    }

    @Test
    void testAlterMarkDouble() {
        field.alterMark();
        field.alterMark();
        assertFalse(field.isMarked());
    }

    @Test
    void testOpenNotMinedNotMarked() {
        assertTrue(field.open());
    }

    @Test
    void testOpenNotMinedMarked() {
        field.alterMark();
        assertFalse
                (field.open());
    }

    @Test
    void testOpenMinedMarked() {
        field.makeMined();
        field.alterMark();
        assertFalse(field.open());

    }

    @Test
    void testOpenMinedNotMarked() {
        field.makeMined();

        assertThrows(ExplosionException.class, () -> {
            field.open();
        });

    }

    @Test
    void testOpenWithNeighbours() {
        Field neighbour = new Field(2,2);
        Field neighbour1 = new Field(1,1);

        neighbour.addNeighbour(neighbour1);
        field.addNeighbour(neighbour);

        field.open();

        assertTrue(neighbour.isOpened() && neighbour1.isOpened());
    }

    @Test
    void testOpenWithNeighboursFalse() {
        Field neighbour = new Field(2,2);
        Field neighbour1 = new Field(1,1);

        neighbour.addNeighbour(neighbour1);
        field.addNeighbour(neighbour);
        neighbour.makeMined();

        field.open();


        assertFalse(neighbour.isOpened() && neighbour1.isOpened());
    }

}
