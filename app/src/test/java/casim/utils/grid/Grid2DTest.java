package casim.utils.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import casim.utils.Result;
import casim.utils.coordinate.CoordinatesUtil;
import casim.utils.range.Ranges;

/**
 * Test class for {@link Grid2D}.
 */
public class Grid2DTest {

    private static final int DEFAULT_VALUE = 1;
    private static final int ROWS = 3;
    private static final int COLS = 2;
    private static final int X = 0;
    private static final int Y = 1;

    private Grid2D<Integer> getGrid() {
        return new Grid2DImpl<>(ROWS, COLS, () -> DEFAULT_VALUE);
    }

    private Grid2D<Integer> getRandomGrid() {
        return new Grid2DImpl<>(ROWS, COLS,
            () -> (int)Math.round(Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Test for {@link Grid#get(int, int)} method.
     */
    @Test
    void testGetWithIntegers() {
        final var grid = getGrid();
        assertEquals(Result.ofEmpty(), grid.set(X, Y, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, grid.get(X, Y));
        assertEquals(IndexOutOfBoundsException.class, grid.get(X, COLS).getError().getClass());
        assertEquals(IndexOutOfBoundsException.class, grid.get(ROWS, Y).getError().getClass());
    }

    /**
     * Test for {@link Grid#get(casim.utils.coordinate.Coordinates)} method.
     */
    @Test
    void testGetWithCoordinates() {
        final var grid = getGrid();
        final var coord = CoordinatesUtil.of(X, Y);
        assertEquals(Result.ofEmpty(), grid.set(coord, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, grid.get(coord));
        assertEquals(
            IndexOutOfBoundsException.class, grid.get(CoordinatesUtil.of(X, COLS)).getError().getClass());
        assertEquals(
            IndexOutOfBoundsException.class, grid.get(CoordinatesUtil.of(ROWS, Y)).getError().getClass());
    }

    /**
     * Test for {@link Grid#getHeight()} method.
     */
    @Test
    void testGetHeight() {
        assertEquals(ROWS, getGrid().getHeight());
    }
    
    /**
     * Test for {@link Grid#getWidth()} method.
     */
    @Test
    void testGetWidth() {
        assertEquals(COLS, getGrid().getWidth());
    }

    /**
     * Test for {@link Grid#isCoordValid(casim.utils.coordinate.Coordinates)} method.
     */
    @Test
    void testIsCoordValid() {
        final var grid = getGrid();
        for (final var x : Ranges.of(0, ROWS)) {
            for (final var y : Ranges.of(0, COLS)) {
                assertTrue(grid.isCoordValid(CoordinatesUtil.of(x, y)));
            }
        }
        assertFalse(grid.isCoordValid(CoordinatesUtil.of(-1, 0)));
        assertFalse(grid.isCoordValid(CoordinatesUtil.of(0, -1)));
        assertFalse(grid.isCoordValid(CoordinatesUtil.of(-1, -1)));
        assertFalse(grid.isCoordValid(CoordinatesUtil.of(ROWS, 0)));
        assertFalse(grid.isCoordValid(CoordinatesUtil.of(0, COLS)));
        assertFalse(grid.isCoordValid(CoordinatesUtil.of(ROWS, COLS)));
    }

    @Test
    void testSetWithIntegers() {
        final int newVal = 2;
        final var grid = getGrid();
        assertEquals(Result.ofEmpty(), grid.set(X, Y, DEFAULT_VALUE));
        assertEquals(Result.of(newVal), grid.get(X, Y));
        assertEquals(IndexOutOfBoundsException.class, grid.set(X, COLS, newVal).getError().getClass());
        assertEquals(IndexOutOfBoundsException.class, grid.set(ROWS, Y, newVal).getError().getClass());
    }

    @Test
    void testSetWithCoordinates() {
        final int newVal = 2;
        final var coord = CoordinatesUtil.of(X, Y);
        final var grid = getGrid();
        assertEquals(Result.ofEmpty(), grid.set(coord, DEFAULT_VALUE));
        assertEquals(Result.of(newVal), grid.get(coord));
        assertEquals(
            IndexOutOfBoundsException.class, grid.set(CoordinatesUtil.of(X, COLS), newVal).getError().getClass());
        assertEquals(
            IndexOutOfBoundsException.class, grid.set(CoordinatesUtil.of(ROWS, Y), newVal).getError().getClass());
    }

    @Test
    void testStream() {
        final var grid = getRandomGrid();
        final var elements = new HashSet<>();
        for (final var x : Ranges.of(0, ROWS)) {
            for (final var y : Ranges.of(0, COLS)) {
                elements.add(grid.get(x, y).getValue());
            }
        }
        assertEquals(elements, grid.stream().collect(Collectors.toUnmodifiableSet()));
    }
}
