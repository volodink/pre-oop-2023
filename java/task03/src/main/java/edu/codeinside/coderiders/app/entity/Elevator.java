package edu.codeinside.coderiders.app.entity;

/**
 * Elevator entity class.
 */
public class Elevator {
    /**
     *
     */
    private int id;

    /**
     *
     */
    private int floor;

    /**
     *  Default Elevator constructor by given id.
     * @param elevator_id Elevator identifier
     */
    public Elevator(int elevator_id) {
        this.id = elevator_id;
        this.floor = 1;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return Integer
     */
    public int getFloor() {
        return floor;
    }

    /**
     *
     * @param floor
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Elevator(" +
                "id=" + id +
                ", floor=" + floor +
                ')';
    }
}
