/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package casim;

/**
 * App class.
 */
public class App {
    /**
     * Greetings function.
     * 
     * @return greetings
     */
    public String getGreeting() {
        return "Hello World!";
    }

    /**
     * Entry Point.
     * 
     * @param args args
     */
    public static void main(final String[] args) {
        System.out.println(new App().getGreeting());
    }
}
