package casim.model.cell;

/**
 * A {@link Cell} of the {@link casim.utils.grid.Grid}.
 * 
 *  @param <T> the type of the finite states of the {@link casim.model.Automaton}'s {@link Cell}.
 */
public interface Cell<T> {

    /** 
     * Return the current state of the {@link Cell}.
     * 
     * @return the current state of the {@link Cell}.
     */
    T getState();

}
