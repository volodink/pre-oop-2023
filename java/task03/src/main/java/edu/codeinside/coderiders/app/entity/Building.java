package edu.codeinside.coderiders.app.entity;

import edu.codeinside.coderiders.app.entity.ElevatorStateItem;
import edu.codeinside.coderiders.app.entity.Elevator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Building entity class.
 */
public class Building {

    /**
     *
     */
    private static final Logger
            LOGGER = LogManager.getLogger(Class.class.getName());

    /**
     * Elevators in building list.
     * Defaults to empty list.
     */
    private final ArrayList<Elevator> elevators = new ArrayList<>();
    /**
     * Building floor count.
     * Default value = 9.
     */
    private int floorCount = 9;
    /**
     * Building elevator count.
     * Default value = 3.
     */
    private int elevatorCount = 3;

    /**
     * Default constructor
     */
    public Building() {
        LOGGER.info("Building default building.");

        // Initialize elevators list
        IntStream.range(0, this.elevatorCount).forEach(elevator_id -> this.elevators.add(new Elevator(elevator_id)));

        LOGGER.debug(new StringBuilder()
                .append("Elevators in building: ")
                .append(this.elevators));
        LOGGER.info(new StringBuilder()
                .append("Building init done. The building has ")
                .append(this.floorCount)
                .append(" floors and ")
                .append(this.elevatorCount)
                .append(" elevators.")
        );
    }

    /**
     * General parametrized building constructor
     *
     * @param floor_count    Building floor count
     * @param elevator_count Building elevator count
     */
    public Building(int floor_count, int elevator_count) {
        LOGGER.info("Building custom building.");

        this.floorCount = floor_count;
        this.elevatorCount = elevator_count;

        // Initialize elevators list
        IntStream.range(0, this.elevatorCount).forEach(elevator_id -> this.elevators.add(new Elevator(elevator_id)));

        LOGGER.debug(new StringBuilder()
                .append("Elevators in building: ")
                .append(this.elevators));
        LOGGER.info(new StringBuilder()
                .append("Building init done. The building has ")
                .append(this.floorCount)
                .append(" floors and ")
                .append(this.elevatorCount)
                .append(" elevators.")
        );
    }

    /**
     * Returns state of the building.
     * The state of the building can be described as a list of elements, which is actually a pair of
     * integer values, e.g. <elevator_id, current_floor>.
     *
     * @return List of state items
     */
    private ArrayList<ElevatorStateItem> getState() {

        LOGGER.info("Getting building state...");

        ArrayList<ElevatorStateItem> result = new ArrayList<>();

        this.elevators.forEach(elevator -> result.add(new ElevatorStateItem(elevator.getId(), elevator.getFloor())));

        return result;
    }

    /**
     * Returns the nearest elevator id by given user floor and optionally excluding particular elevators ids.
     *
     * @param userFloor  User floor now.
     * @param excludeIds Elevator ids that must be excluded from search.
     * @return Nearest elevator id.
     */
    private Integer getNearestElevatorId(Integer userFloor, ArrayList<Integer> excludeIds) {
        ArrayList<Integer> finalExcludeIds;

        if (excludeIds == null || excludeIds.isEmpty()) {
            finalExcludeIds = new ArrayList<>();
        } else {
            finalExcludeIds = excludeIds;
        }

        LOGGER.info(new StringBuilder()
                .append("Getting nearest elevator id to ")
                .append(userFloor)
                .append(", excluding elevators: ")
                .append(excludeIds)
        );

        ArrayList<ElevatorStateItem> buildingState = this.getState();

        LOGGER.debug(new StringBuilder()
                .append("Building state now is -> ")
                .append(buildingState)
        );

        ArrayList<Pair> distances = new ArrayList<>();

        for (ElevatorStateItem state : buildingState) {
            boolean result = finalExcludeIds.stream().anyMatch(id -> state.getId() == id);
            if (!result) {
                Pair<Integer, Integer> pair = Pair.with(state.getId(),
                        Math.abs(userFloor - state.getCurrentFloor()));
                distances.add(pair);
            }
        }

        LOGGER.debug(new StringBuilder()
                .append("Distances: ")
                .append(distances)
        );

        ArrayList<Pair> sorted_distances = (ArrayList<Pair>) distances
                .stream()
                .sorted(Comparator.comparing(Pair<Integer, Integer>::getValue1))
                .collect(Collectors.toList());

        LOGGER.info("Sorting by distance increase.");

        LOGGER.debug(new StringBuilder()
                .append("Sorted distances: ")
                .append(sorted_distances)
        );

        Integer nearest_elevator_id = (Integer) sorted_distances.get(0).getValue(0);

        LOGGER.info(new StringBuilder()
                .append("Minimum distance: ")
                .append(sorted_distances.get(0).getValue(1))
        );

        LOGGER.info(new StringBuilder()
                .append("Nearest elevator id: ")
                .append(nearest_elevator_id)
        );

        return nearest_elevator_id;
    }

    /**
     * The elevator button click implementation.
     *
     * @param userFloor Integer User floor now.
     * @return Integer Arrived elevator id.
     */
    public Integer callElevator(Integer userFloor) {
        if ((userFloor >= 1) && (userFloor <= 9)) {

            LOGGER.info(new StringBuilder()
                    .append("Floor value in allowed range, we will proceed.")
            );

            System.out.println(userFloor);
        } else {
            LOGGER.error(new StringBuilder()
                    .append("The floor ")
                    .append(userFloor)
                    .append(" does not exists! Where did you get The Button? O_O")
            );

            return null;
        }

        // 1. Send elevator to 1st floor if there is no such
        LOGGER.info("System is updating...");
        LOGGER.info("hecking first floor elevator availability...");

        Pair<Boolean, Integer> firstFloorPair = this.isElevatorAvailable(1);
        Boolean first_floor_elevator_available = firstFloorPair.getValue0();
        Integer first_floor_elevator_id = firstFloorPair.getValue1();

        if (first_floor_elevator_available) {
            LOGGER.info(new StringBuilder()
                    .append("Elevator ")
                    .append(first_floor_elevator_id)
                    .append(" available at 1st floor. Nice!")
            );
        } else {
            LOGGER.info("There is no elevators on first floor...");
            LOGGER.info("Getting elevator, nearest to the 1st floor.");

            first_floor_elevator_id = this.getNearestElevatorId(1, null);

            LOGGER.info(new StringBuilder()
                    .append("Sent elevator ")
                    .append(first_floor_elevator_id)
                    .append(" at 1st floor. Reason: no elevators on 1st floor")
            );

            this.moveElevator(first_floor_elevator_id, 1);
        }

        // 2. Check available elevator on floor if yes -> return such
        Pair<Boolean, Integer> elevatorPair = this.isElevatorAvailable(userFloor);
        Boolean elevator_available = elevatorPair.getValue0();
        Integer elevator_id = elevatorPair.getValue1();
        if (elevator_available) {
            LOGGER.info(new StringBuilder()
                    .append("Elevator ")
                    .append(elevator_id)
                    .append(" available on ")
                    .append(userFloor)
                    .append(".")
            );
            return elevator_id;
        } else {
            LOGGER.info(new StringBuilder()
                    .append("Elevator ")
                    .append(elevator_id)
                    .append(" NOT available on ")
                    .append(userFloor)
                    .append(", will call then.")
            );
        }

        // 3. Finally, if there is no elevator on floor, send nearest elevator.
        LOGGER.info(new StringBuilder()
                .append("Getting nearest elevator to ")
                .append(userFloor)
                .append(" floor.")
        );
        Integer nearest_elevator_id = this.getNearestElevatorId(userFloor,
                new ArrayList<Integer>(List.of(first_floor_elevator_id)));

        // 4. Send elevator to 'user_on_floor' floor
        LOGGER.info(new StringBuilder()
                .append("Sending ")
                .append(nearest_elevator_id)
                .append(" to ")
                .append(userFloor)
                .append(".")
        );

        this.moveElevator(nearest_elevator_id, userFloor);

        // 5. Elevator is moving
        LOGGER.warn("Mind the gap! OMG! Elevator is moving... O_O");

        // 6. Elevator arrived at 'user_on_floor' floor
        LOGGER.info(new StringBuilder()
                .append("Elevator ")
                .append(nearest_elevator_id)
                .append(" arrived to floor ")
                .append(userFloor)
                .append(".")
        );

        LOGGER.info(new StringBuilder()
                .append("Building state now is -> ")
                .append(this.getState())
        );

        return nearest_elevator_id;
    }

    /**
     * Checks if elevator available on given floor
     * @param floor
     * @return
     */
    private Pair isElevatorAvailable(Integer floor) {

        LOGGER.info("isElevatorAvailable enter...");

        for (Elevator elevator : this.elevators) {
            if (elevator.getFloor() == floor) {
                return Pair.with(Boolean.TRUE, elevator.getId());
            }
        }

        return Pair.with(Boolean.FALSE, null);
    }

    /**
     * @param elevatorId
     * @param floor
     */
    private void moveElevator(Integer elevatorId, Integer floor) {

        LOGGER.info("moveElevator enter...");

        for (Elevator elevator : this.elevators) {
            if (elevator.getId() == elevatorId) {
                elevator.setFloor(floor);
                break;
            }
        }
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Building(" +
                "floor_count=" + floorCount +
                ", elevator_count=" + elevatorCount +
                ", elevators_state=" + this.getState() +
                ')';
    }
}
