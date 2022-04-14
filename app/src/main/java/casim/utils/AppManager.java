package casim.utils;

import casim.controller.automaton.AutomatonController;
import casim.controller.automaton.AutomatonControllerImpl;
import casim.controller.automaton.CoDiControllerImpl;
import casim.controller.menu.MenuControllerImpl;
import casim.model.Automata;
import casim.model.abstraction.cell.AbstractCell;
import casim.model.bryansbrain.BryansBrainCellState;
import casim.model.codi.CoDiConfig;
import casim.model.codi.cell.attributes.CoDiCellState;
import casim.model.gameoflife.GameOfLifeState;
import casim.model.langtonsant.LangtonsAntCellState;
import casim.model.langtonsant.LangtonsAntConfig;
import casim.model.rule110.Rule110CellState;
import casim.model.wator.WatorCellState;
import casim.ui.components.grid.CanvasGridBuilderImpl;
import casim.ui.components.grid.CanvasGridImpl;
import casim.ui.components.menu.automaton.AutomatonMenu;
import casim.ui.components.menu.automaton.config.AutomatonConfigController;
import casim.ui.components.menu.automaton.config.AutomatonWrapConfigController;
import casim.ui.components.page.PageContainer;
import casim.ui.utils.StateColorMapper;
import casim.ui.utils.StateColorMapperFactory;
import casim.ui.utils.ViewEnum;
import casim.ui.view.AutomatonViewController;
import casim.ui.view.ConcurrentAutomatonViewController;
import casim.utils.automaton.AutomatonFactoryImpl;
import casim.utils.automaton.config.BaseConfig;
import casim.utils.automaton.config.WrappingConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 * App manager that allows to display the correct view on screen.
 */
public final class AppManager {
    private static final String UNKNOWN_AUTOMATON = "Unknown automaton.";
    private static final AutomatonFactoryImpl AUTOMATON_FACTORY = new AutomatonFactoryImpl();

    /**
     * Creates the main menu and shows it.
     * 
     * @param container the view container.
     */
    public static void showMainMenu(final PageContainer container) {
        final var controller = new MenuControllerImpl();
        final var menu = new AutomatonMenu(container, controller);
        container.addPage(menu);
    }

    /**
     * Shows the right configuration menu for the chosen automaton.
     * 
     * @param automata the automaton to configure.
     * @param container the view container.
     * @return {@link Result} holding {@link Empty} if there is no error, an exception otherwise.
     */
    public static Result<Empty> showConfigMenu(final Automata automata, final PageContainer container) {
        final var loader = new FXMLLoader(AppManager.class.getResource(ViewEnum.AUTOMATON_CONFIG_MENU.getResourceName()));
        final var controller = getConfigController(automata, container);
        loader.setController(controller);
        final Result<VBox> view = Result.executeSupplier(() -> (VBox) loader.load());
        view.ifPresent(container::addPage);
        return view.map(x -> new Empty() { });
    }

    /**
     * Shows the simulation view for the chosen automaton.
     * 
     * @param automata the automata to simulate.
     * @param container the view container.
     * @param config the configuration object.
     * @return {@link Result} holding {@link Empty} if there is no error, an exception otherwise.
     */
    public static Result<Empty> showSimulation(final Automata automata, final PageContainer container, final BaseConfig config) {
        final var loader = new FXMLLoader(AppManager.class.getResource(ViewEnum.AUTOMATON_VIEW.getResourceName()));
        final var viewController = getAutomatonViewControllerFromAutomata(automata, container, config);
        loader.setController(viewController);
        final Result<VBox> view = Result.executeSupplier(() -> (VBox) loader.load());
        view.ifPresent(container::addPage);
        return view.map(x -> new Empty() { });
    }

    //TODO: ask
    @SuppressWarnings("unchecked")
    private static <T extends AutomatonViewController<?>> T getAutomatonViewControllerFromAutomata(final Automata automata, final PageContainer container, final BaseConfig config) {
        switch (automata) {
            case CODI:
                return (T) getCoDiViewController(container, (CoDiConfig) config);
            case RULE110:
                return (T) getRule110ViewController(container, config);
            case WATOR:
                return (T) getWatorViewController(container, config);
            case BRYANS_BRAIN:
                return (T) getBryansBrainViewController(container, (WrappingConfig) config);
            case GAME_OF_LIFE:
                return (T) getGameOfLifeViewController(container, (WrappingConfig) config);
            case LANGTONS_ANT:
                return (T) getLangtonsAntViewController(container, (LangtonsAntConfig) config);
            default:
                throw new IllegalArgumentException(UNKNOWN_AUTOMATON);
        }
    }

    private static AutomatonViewController<BryansBrainCellState> getBryansBrainViewController(final PageContainer container, final WrappingConfig config) {
        final var controller = new AutomatonControllerImpl<>(AUTOMATON_FACTORY.getBryansBrainRandom(config));
        return getAutomatonViewController(
            container,
            controller,
            getGrid(config.getCols(), config.getRows()),
            StateColorMapperFactory.getBryansBrainStateColorMapper(),
            config.isAutomatic());
    }

    private static AutomatonViewController<CoDiCellState> getCoDiViewController(final PageContainer container, final CoDiConfig config) {
        final var controller = new CoDiControllerImpl(AUTOMATON_FACTORY.getCoDi(config));
        return getAutomatonViewController(
            container,
            controller,
            getGrid(config.getCols(), config.getRows()),
            StateColorMapperFactory.getCoDiStateColorMapper(),
            config.isAutomatic());
    }

    private static AutomatonViewController<WatorCellState> getWatorViewController(final PageContainer container, final BaseConfig config) {
        final var controller = new AutomatonControllerImpl<>(AUTOMATON_FACTORY.getWator(config));
        return getAutomatonViewController(
            container,
            controller,
            getGrid(config.getCols(), config.getRows()),
            StateColorMapperFactory.getWatorStateColorMapper(),
            config.isAutomatic());
    }

    private static AutomatonViewController<LangtonsAntCellState> getLangtonsAntViewController(final PageContainer container, final LangtonsAntConfig config) {
        final var controller = new AutomatonControllerImpl<>(AUTOMATON_FACTORY.getLangtonsAnt(config));
        return getAutomatonViewController(
            container,
            controller,
            getGrid(config.getCols(), config.getRows()),
            StateColorMapperFactory.getLangtonsAntStateColorMapper(),
            config.isAutomatic());
    }

    private static AutomatonViewController<Rule110CellState> getRule110ViewController(final PageContainer container, final BaseConfig config) {
        final var controller = new AutomatonControllerImpl<>(AUTOMATON_FACTORY.getRule110(config));
        return getAutomatonViewController(
            container,
            controller,
            getGrid(config.getCols(), config.getRows()),
            StateColorMapperFactory.getRule110StateColorMapper(),
            config.isAutomatic());
    }

    private static AutomatonViewController<GameOfLifeState> getGameOfLifeViewController(final PageContainer container, final WrappingConfig config) {
        final var controller = new AutomatonControllerImpl<>(AUTOMATON_FACTORY.getGameOfLife(config));
        return getAutomatonViewController(
            container,
            controller,
            getGrid(config.getCols(), config.getRows()),
            StateColorMapperFactory.getGameOfLifeStateColorMapper(),
            config.isAutomatic());
    }

    private static <T, C extends AbstractCell<T>> AutomatonViewController<T> getAutomatonViewController(
            final PageContainer container, final AutomatonController<T> controller, final CanvasGridImpl grid, final StateColorMapper<T> mapper, final boolean isAutomatic) {
        return isAutomatic
            ? new ConcurrentAutomatonViewController<>(container, controller, grid, mapper)
            : new AutomatonViewController<>(container, controller, grid, mapper);
    } 

    private static AutomatonConfigController getConfigController(final Automata automata, final PageContainer container) {
        switch (automata) {
            case CODI:
            case RULE110:
            case WATOR:
                return new AutomatonConfigController(container, automata);
            case BRYANS_BRAIN:
            case GAME_OF_LIFE:
            case LANGTONS_ANT:
                return new AutomatonWrapConfigController(container, automata);
            default:
                throw new IllegalArgumentException(UNKNOWN_AUTOMATON);
        }
    }

    private static CanvasGridImpl getGrid(final int cols, final int rows) {
        return (CanvasGridImpl) new CanvasGridBuilderImpl().build(rows, cols);
    }

    private AppManager() {

    }
}
