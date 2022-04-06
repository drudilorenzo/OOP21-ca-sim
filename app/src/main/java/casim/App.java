/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package casim;

import casim.controller.automaton.AutomatonController;
import casim.controller.automaton.AutomatonControllerImpl;
import casim.model.bryansbrain.BryansBrain;
import casim.model.bryansbrain.BryansBrainCellState;
import casim.ui.components.grid.CanvasGridBuilderImpl;
import casim.ui.components.grid.CanvasGridImpl;
import casim.ui.components.page.PageContainer;
import casim.ui.utils.StateColorMapper;
import casim.ui.view.AutomatonViewController;
import casim.utils.Colors;
import casim.utils.grid.Grid2DImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.GraphicsEnvironment;
import java.io.IOError;
import java.io.IOException;
import java.util.Random;

/**
 * Main project class.
 */
public class App extends Application {

    final static int ROWS = 200;
    final static int COLS = 200;
    final static int DEPTH = 100;

    private CanvasGridImpl getGrid() {
        final var grid = new CanvasGridBuilderImpl().build(ROWS, COLS);
        return (CanvasGridImpl)grid;
    }

    private void startBryansBrainFXML(final Stage primaryStage) throws IOException {
        final var rng = new Random();
        final var grid = new Grid2DImpl<BryansBrainCellState>(ROWS, COLS, () -> {
            final var states = BryansBrainCellState.values();
            return states[rng.nextInt(states.length)];
        });
        final var automaton = new BryansBrain(grid, true);
        final var controller = new AutomatonControllerImpl<>(automaton);
        final var root = new PageContainer(primaryStage);
        final var loader = new FXMLLoader(getClass().getResource("/automatonView.fxml"));
        final var viewController = new AutomatonViewController<BryansBrainCellState>();
        loader.setController(viewController);
        final var view = (VBox) loader.load();
        viewController.initData(root, controller, this.getGrid(), s -> {
            switch (s) {
            case ALIVE:
                return Colors.WHITE;
            case DEAD:
                return Colors.BLACK;
            case DYING:
                return Colors.LIGHT_BLUE;
            default:
                throw new IllegalArgumentException("Invalid state.");
            }
        });

        final var graphics = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        final var width = graphics.getDisplayMode().getWidth();
        final var height = graphics.getDisplayMode().getHeight();

        primaryStage.setWidth(width / 2);
        primaryStage.setHeight(height / 2);

        root.addPage(view);
        final var scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
        

    /**
     * Entry point.
     * 
     * @param args command line args.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.startBryansBrainFXML(primaryStage);
    }
}
