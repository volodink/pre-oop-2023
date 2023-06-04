import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import edu.codeinside.coderiders.app.entity.*;

public class Task03Tests {

    @Test
    void test_elevator_get_id() {
        Elevator elevator = new Elevator(1);
        assertEquals(1, elevator.getId());
    }

    @Test
    void test_elevator_set_get_floor() {
        Elevator elevator = new Elevator(1);
        elevator.setFloor(2);
        assertEquals(2, elevator.getFloor());
    }

    @Test
    void test_user_input_is_correct() {
        Building building = new Building(9, 3);

        assertEquals(null, building.callElevator(-1));
    }

    @Test
    void test_custom_building_elevators_state_is_correct() {
        Building building = new Building(5, 2);

        assertEquals(1, building.callElevator(2));
        assertEquals(0, building.callElevator(1));
        assertEquals(1, building.callElevator(9));
        assertEquals(1, building.callElevator(3));
    }

    @Test
    void test_building_default_constructor_elevators_state_is_correct() {
        Building building = new Building();

        assertEquals(1, building.callElevator(2));
        assertEquals(0, building.callElevator(1));
        assertEquals(1, building.callElevator(9));
        assertEquals(2, building.callElevator(3));
    }

    @Test
    void test_building_custom_constructor_elevators_state_is_correct() {
        Building building = new Building(9,3);

        assertEquals(1, building.callElevator(2));
        assertEquals(0, building.callElevator(1));
        assertEquals(1, building.callElevator(9));
        assertEquals(2, building.callElevator(3));
    }

    @Test
    @RepeatedTest(10)
    void test_elevators_state_not_changing_same_floor_call() {
        Building building = new Building(9,3);

        assertEquals(1, building.callElevator(2));
        assertEquals(1, building.callElevator(9));
    }

}
