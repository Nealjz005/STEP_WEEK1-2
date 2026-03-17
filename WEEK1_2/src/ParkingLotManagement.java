import java.util.*;

class ParkingSlot {
    String vehicleNumber;
    boolean isOccupied;

    ParkingSlot() {
        this.vehicleNumber = null;
        this.isOccupied = false;
    }
}

class ParkingLot {
    private ParkingSlot[] table;
    private int capacity;
    private int size;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.table = new ParkingSlot[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new ParkingSlot();
        }
        this.size = 0;
    }

    // Hash function
    private int hash(String vehicle) {
        return Math.abs(vehicle.hashCode()) % capacity;
    }

    // Park vehicle
    public void park(String vehicle) {
        int index = hash(vehicle);
        int start = index;

        while (table[index].isOccupied) {
            index = (index + 1) % capacity; // linear probing
            if (index == start) {
                System.out.println("Parking Full!");
                return;
            }
        }

        table[index].vehicleNumber = vehicle;
        table[index].isOccupied = true;
        size++;

        System.out.println(vehicle + " parked at slot " + index);
    }

    // Remove vehicle
    public void leave(String vehicle) {
        int index = hash(vehicle);
        int start = index;

        while (table[index].isOccupied) {
            if (vehicle.equals(table[index].vehicleNumber)) {
                table[index].isOccupied = false;
                table[index].vehicleNumber = null;
                size--;
                System.out.println(vehicle + " left slot " + index);
                return;
            }
            index = (index + 1) % capacity;
            if (index == start) break;
        }

        System.out.println("Vehicle not found!");
    }

    public void getStats() {
        double occupancy = (size * 100.0) / capacity;
        System.out.println("Occupancy: " + occupancy + "%");
    }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(10);

        lot.park("ABC1234");
        lot.park("XYZ5678");
        lot.park("LMN1111");

        lot.leave("XYZ5678");

        lot.getStats();
    }
}