package edu.codeinside.coderiders.app.entity;

/**
 *  Elevator state item class
 */
public class ElevatorStateItem {
    /**
     *  Elevator id
     */
    private int id;

    /**
     *  Elevator current floor
     */
    private int currentFloor;

    /**
     *  Class default constructor
     * @param id Elevator id
     * @param floor Elevator current floor
     */
    public ElevatorStateItem(int id, int floor) {
        this.id = id;
        this.currentFloor = floor;
    }

    /**
     *  Elevator floot getter.
     * @return
     */
    public int getCurrentFloor() {
        return currentFloor;
    }

    /**
     *
     * @param currentFloor
     */
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" +
                id + ", " +
                currentFloor +
                ']';
    }
}
