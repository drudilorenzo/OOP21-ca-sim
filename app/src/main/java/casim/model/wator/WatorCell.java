package casim.model.wator;

import casim.model.abstraction.cell.AbstractCell;

public class WatorCell extends AbstractCell<CellState>{

    private static final int MIN_HEALTH = 0;

    private final int max_health;
    private int health;

    public WatorCell(CellState state, final int health, final int max_health) {
        super(state);
        this.health = health;
        this.max_health = max_health;
    }


    /**
     * Returns the health value of the {@link WatorCell}.
     * @return integer value representing the current health
     * of the {@link WatorCell}
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Returns the maximum health value of the {@link WatorCell}.
     * 
     * @return the interger value representing the maximum health
     * of the {@link WatorCell}
     */
    public int getMaxHealth() {
        return this.max_health;
    }

    /**
     * Sets the current health value of the {@link WatorCell}.
     * 
     * @param health the integer value to be set as
     * the current health value of the {@link WatorCell}.
     * Must be greater or equal to 0 and smaller or equal to 
     * the maximum health value.
     * 
     * @throws IllegalArgumentException if the value given as
     * argument is less than 0 or greater than the maximum health
     * value.
     */
    public void setHealth(final int health) {
        if (health < MIN_HEALTH || health > this.max_health) {
            throw new IllegalArgumentException("Heath value out of valid limits.");
        }
        this.health = health;
    }
    
    /**
     * Returns true if the {@link WatorCell} has
     * reached minimum health value, false otherwise.
     * 
     * @return true if the {@link WatorCell} has run
     * out of health, false otherwise
     */
    public boolean isDead() {
        return this.health == MIN_HEALTH;
    }

    /**
     * Heals the {@link WatorCell} if health isn't at
     * maximum.
     */
    public void heal() {
        this.health += this.health == this.max_health ? 0 : 1;
    }

    /**
     * Diminishes the health of the {@link WatorCell} by
     * 1 if health isn't at minimum.
     */
    public void starve() {
        this.health =- (this.isDead() ? 0 : 1);
    }

}
