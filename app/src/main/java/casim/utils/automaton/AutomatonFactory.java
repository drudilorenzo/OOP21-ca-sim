package casim.utils.automaton;

import casim.model.bryansbrain.BryansBrain;
import casim.model.codi.CoDi;
import casim.model.langtonsant.LangtonsAnt;
import casim.model.langtonsant.LangtonsAntConfig;
import casim.model.wator.Wator;
import casim.utils.automaton.config.BaseConfig;
import casim.utils.automaton.config.WrappingConfig;

/**
 * A factory for the automaton creation.
 */
public interface AutomatonFactory {

    /**
     * Return a new {@link BryansBrain} automaton.
     * 
     * @param config the {@link ByansBrainConfig} containing
     *          the automaton's configuration values.
     * @return a new {@link BryansBrain} automaton.
     */
    BryansBrain getBryansBrainRandom(WrappingConfig config);

    /**
     * Return a new {@link CoDi} automaton.
     * 
     * @param cols the cols of the automaton grid.
     * @param rows the rows of the automaton grid.
     * @param depth the depth of the automaton's grid.
     * @return a new {@link CoDi} automaton.
     */
    CoDi getCoDi(int cols, int rows, int depth);

    /**
     * Returns a new {@link LangtonsAnt} automaton.
     * 
     * @param config the {@link LangtonsAntConfig} containing
     *          the automaton's configuration values.
     * @return a new {@link LangtonsAnt} automaton.
     */
    LangtonsAnt getLangtonsAnt(LangtonsAntConfig config);

    /**
     * Returns a new {@link Wator} automaton.
     * 
     * @param config the {@link BaseConfig} containing
     *          the automaton's configuration values.
     * @return a new {@link Wator} automaton.
     */
    Wator getWator(BaseConfig config);
}
