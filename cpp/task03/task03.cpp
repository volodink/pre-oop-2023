#include <iostream>
#include <vector>
#include <ostream>
#include <algorithm>

using namespace std;

class ElevatorStateItem {
    private:
        int id;
        int current_floor;
    
    public:
        ElevatorStateItem(int id, int floor) {
            this->id = id;
            this->current_floor = floor;
        };
        int get_current_floor() { return this->current_floor; };
        void set_current_floor(int floor) { this->current_floor = floor; };
        int get_id() { return this->id; };
        void set_id(int id) { this->id = id; };
        friend std::ostream& operator<<(std::ostream& strm, const ElevatorStateItem& state_item) {
            return strm << "ElevatorStateItem(id=" << state_item.id << ", current_floor=" << state_item.current_floor << ")";
        };        
};

class Elevator {
    private:
        int id;
        int floor;
    public:
        Elevator(int id) {
            this->id = id;
            this->floor = 1;
        };
        int get_id() { return id; };
        int get_floor() { return floor; };
        void set_floor(int id) { floor = id; };
        friend std::ostream& operator<<(std::ostream& strm, const Elevator& elevator) {
            return strm << "Elevator(id=" << elevator.id << ", floor=" << elevator.floor << ")";
        };
};

class Building {
    private:
        // Panelki vibe )
        int floor_count = 9;
        int elevator_count = 3;
        vector<Elevator> elevators;

        vector<ElevatorStateItem> get_state() {
            vector<ElevatorStateItem> state;
            for (auto &elevator : elevators) {
                state.push_back(ElevatorStateItem(elevator.get_id(), elevator.get_floor()));
            }
            return state;
        };

        int get_nearest_elevator_id(int user_floor, vector<int> &exclude_ids) {

            vector<int> final_exclude_ids;

            if (exclude_ids.empty()){
                ;
            } else {
                final_exclude_ids = exclude_ids;
            }

            vector<ElevatorStateItem> building_state = this->get_state();
            vector<pair<int, int>> distances;

            for (auto& state: building_state) {
                bool result = false;
                for (auto& e : final_exclude_ids)  {
                    if (e == state.get_id()) {
                        result = true;
                        break;
                    }
                }
                     
                if (!result) {
                    pair<int, int> p = pair<int, int>(state.get_id(),
                                                  abs(user_floor - state.get_current_floor())
                                                 );
                    distances.push_back(p);
                }
            }

            struct
            {
                bool operator()(pair<int,int> a, pair<int, int> b) const { return a.second < b.second; }
            }
            customLess;

            std::sort(distances.begin(), distances.end(), customLess);

            int nearest_elevator_id = distances[0].first;

            return nearest_elevator_id;
        };

        pair<bool, int> is_elevator_available(int floor) {
            for (auto &elevator :elevators) {
                if (elevator.get_floor() == floor) {
                    return pair<bool, int>(true, elevator.get_id());
                }
            }

            return pair<bool, int>(false, NULL);
        };

        void move_elevator(int elevator_id, int floor) {
            for (auto &elevator : elevators) {
                if (elevator.get_id() == elevator_id) {
                    elevator.set_floor(floor);
                    break;
                }
            }
        };

    public:
        Building() {
            for (int elevator_id = 0; elevator_id < elevator_count; elevator_id++) {
                elevators.insert(elevators.end(), Elevator(elevator_id));
            }
        };
        Building(int floor_count, int elevator_count) {
            this->floor_count = floor_count;
            this->elevator_count = elevator_count;

            for (int elevator_id = 0; elevator_id < elevator_count; elevator_id++) {
                elevators.insert(elevators.end(), elevator_id);
            }
        };
        int get_floor_count() { return floor_count; };
        int get_entrance_count() { return elevator_count; };

        int call_elevator(int user_floor) {
            // 1. Send elevator to 1st floor if there is no such
            pair<bool, int> first_floor_pair = this->is_elevator_available(1);
            bool first_floor_elevator_available = first_floor_pair.first;
            int first_floor_elevator_id = first_floor_pair.second;

            if (first_floor_elevator_available) {
                std::cout << "Elevator is available at 1st floor" << endl;
            }
            else {
                vector<int> empty;
                first_floor_elevator_id = this->get_nearest_elevator_id(1, empty);
                this->move_elevator(first_floor_elevator_id, 1);
            }
            
            // 2. Check available elevator on floor if yes -> return such
            pair<bool, int> elevator_pair = this->is_elevator_available(user_floor);
            bool elevator_available = elevator_pair.first;
            int elevator_id = elevator_pair.second;

            if (elevator_available)
                return elevator_id;
            
            // 3. Finally, if there is no elevator on floor, send nearest elevator.
            vector<int> ff_id;
            ff_id.push_back(first_floor_elevator_id);

            int nearest_elevator_id = this->get_nearest_elevator_id(user_floor, ff_id);

            this->move_elevator(nearest_elevator_id, user_floor);

            std::cout << "Mind the gap! OMG! Elevator is moving... O_O" << endl;

            std::cout << "Elevator " << nearest_elevator_id << " arrived on floor " << user_floor << "." << endl;

            return nearest_elevator_id;
        };






        friend std::ostream& operator<<(std::ostream& strm, Building& building) {
            vector<ElevatorStateItem> state = building.get_state();
            std::string state_string = "";
            for (auto &s : state) {
                state_string += "[" + std::to_string(s.get_id()) + ", " + std::to_string(s.get_current_floor()) + "]";
            }
            return strm << "Building(" << 
                "floor_count=" << building.floor_count << ", " <<
                "elevator_count=" << building.elevator_count << ", " <<
                "elevator_state=" << state_string <<
                ")";
        };
};

class ElevatorApplication {
    private:
        Building* building;
    public:
        ElevatorApplication() {
            building = new Building(9, 3);
        };
        ~ElevatorApplication() {
            delete building;
        };
        void run() {
            std::cout << "Elevator Application running." << endl;
            std::cout << "Enter floor number or * for exit." << endl;
            while (true)
            {
                std::string user_input_string;
                cin >> user_input_string;

                if (!user_input_string.compare("*")) {
                    std::cout << "Got * , will exit now." << endl;
                    break;
                } else {
                    std::cout << "You clicked at floor: ";
                    int floor = atoi(user_input_string.c_str());
                    std::cout << floor << endl;

                    if (floor <= 0 || floor > this->building->get_floor_count()) {
                        std::cout << "Can't send elevator to " << floor << ", sorry.\n" << endl;
                        continue;
                    }

                    std::cout << "Got valid floor number, will proceed to call the elevator." << endl;
                    
                    int elevator_id = this->building->call_elevator(floor);

                    std::cout << "Elevator " << elevator_id << " arrived at " << floor << " floor." << endl;
                }
            }
            
        }
};

int main(int argc, char **argv) {
    ElevatorApplication app;
    
    app.run();
    
    std::cout << "Have a nice day :)" << endl;
    
    return 0;
}
