package casim.model.rule;

import org.apache.commons.lang3.tuple.Pair;

import casim.model.cell.AbstractCell;
import casim.model.cell.Cell;
import casim.utils.coordinate.Coordinates;
import casim.utils.grid.Grid;

/**
 * The {@link casim.model.Automaton}'s rule used to update the {@link Cell}'s state.
 * 
 *  @param <T> the enumeration which contains the finite states of the {@link casim.model.Automaton}'s {@link Cell}.
 */
public interface UpdateRule<T extends Enum<T>> {

     /**
     * Return the updated {@link Cell}.
     * 
     * @param cellPair pair of {@link Coordinates} and {@link Cell} to update;
     * @param grid the {@link Grid} representing the {@link Automaton}.
     * @return the updated {@link Cell}.
     */
    AbstractCell<T> getNextCell(Pair<Coordinates<Integer>, Cell<T>> cellPair, Grid<Coordinates<Integer>, Cell<T>> grid);

}
