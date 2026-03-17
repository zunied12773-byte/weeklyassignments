import java.util.*;

public class ParkingLotManagement {

    private static class Spot {
        String vehicle;
        long entryTime;
        boolean occupied;

        Spot() {
            this.vehicle = null;
            this.entryTime = 0;
            this.occupied = false;
        }
    }

    private final int SIZE = 500;
    private final Spot[] table = new Spot[SIZE];
    private int occupiedCount = 0;
    private int totalProbes = 0;
    private int operations = 0;

    public ParkingLotManagement() {
        for (int i = 0; i < SIZE; i++) table[i] = new Spot();
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % SIZE;
    }

    public String parkVehicle(String plate) {
        int index = hash(plate);
        int probes = 0;

        while (probes < SIZE) {
            int i = (index + probes) % SIZE;
            if (!table[i].occupied) {
                table[i].vehicle = plate;
                table[i].entryTime = System.currentTimeMillis();
                table[i].occupied = true;
                occupiedCount++;
                totalProbes += probes;
                operations++;
                return "Assigned spot #" + i + " (" + probes + " probes)";
            }
            probes++;
        }
        return "Parking Full";
    }

    public String exitVehicle(String plate) {
        int index = hash(plate);
        int probes = 0;

        while (probes < SIZE) {
            int i = (index + probes) % SIZE;
            if (table[i].occupied && table[i].vehicle.equals(plate)) {
                long duration = (System.currentTimeMillis() - table[i].entryTime) / 60000;
                double fee = duration * 0.5;

                table[i].occupied = false;
                table[i].vehicle = null;
                occupiedCount--;

                return "Spot #" + i + " freed, Duration: " + duration + " min, Fee: $" + fee;
            }
            probes++;
        }
        return "Vehicle not found";
    }

    public String getStatistics() {
        double occupancy = (occupiedCount * 100.0) / SIZE;
        double avgProbes = operations == 0 ? 0 : (double) totalProbes / operations;
        return "Occupancy: " + occupancy + "%, Avg Probes: " + avgProbes;
    }

    public static void main(String[] args) {
        ParkingLotManagement system = new ParkingLotManagement();

        System.out.println(system.parkVehicle("ABC-1234"));
        System.out.println(system.parkVehicle("ABC-1235"));
        System.out.println(system.parkVehicle("XYZ-9999"));

        System.out.println(system.exitVehicle("ABC-1234"));
        System.out.println(system.getStatistics());
    }
}