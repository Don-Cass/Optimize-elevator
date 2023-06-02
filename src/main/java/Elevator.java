import java.util.*;

public class Elevator {
    private double resource;
    private float RoundTripTime;
    Random random = new Random();
    private Set<Integer> operating_floor;
    private Building building;

    public Elevator(Building building) {
        this.building = building;
        operating_floor = new TreeSet<>(); // This is where mySet field is initialized.
    }

    public void setOperating_floor(int[] op_floor) {
        for (int num : op_floor) {
            operating_floor.add(num);
        }
    }

    void setOperating_floor_Random(){
        int number_of_floor = random.nextInt(7) + 1; // generate from 1 to 9
        int count = 0;
        while (count < number_of_floor) {
            int num = random.nextInt(7) + 2; // generate from 1 to 8
            if (!operating_floor.contains(num)) { // check if num is already in the set
                operating_floor.add(num);
                count++;
            }
        }
    }

    public int getMaxOperatingFloor() {
        if (operating_floor.isEmpty()) {
            return 0;
        }
        return Collections.max(operating_floor);
    }




    private void calculateRoundTripTime() {
        int maxFloor = getMaxOperatingFloor();
        int operatingFloorCount = operating_floor.size();
        if (operatingFloorCount > 0 && maxFloor > 1) {
            RoundTripTime = 14 * (maxFloor - 1) + 16 * operatingFloorCount;
        } else {
            RoundTripTime = 0;
        }

    }

    public void setResource() {
        calculateRoundTripTime();
    }


    public Set<Integer> getOperating_floor() {
        return new TreeSet<>(operating_floor);
    }

    void distributeResourceToFloors() {

        float totalPassengerCount = 0f;
        for (int floorNumber : operating_floor) {
            totalPassengerCount += building.getUserDistribution().getOrDefault(floorNumber, 0.0);
        }

        for (int floorNumber : operating_floor) {
            double passengerCount = building.getUserDistribution().getOrDefault(floorNumber, 0.0);
            resource = (24.0 * (passengerCount / totalPassengerCount)) / RoundTripTime;
            double resourceToBeAdded = resource;
            if (floorNumber == 2) {
                resourceToBeAdded *= 1; // 20% reduction for floor 2
            }
            if (floorNumber == 3) {
                resourceToBeAdded *= 1; // 20% reduction for floor 3
            }

            Floor floor = building.getFloor(floorNumber);
            floor.addResource(resourceToBeAdded);
        }
    }

    public void printOperatingFloors() {
        System.out.println("This elevator operates on the following floors: " + operating_floor);
    }

    public void printPassengerRatio() {
        System.out.println("Elevator operating floors: " + operating_floor);
        double totalPassengerCount = 0.0;
        Map<Integer, Double> passengerRatios = new HashMap<>();

        for (Integer floorNumber : operating_floor) {
            double floorUserDistribution = building.getUserDistribution().getOrDefault(floorNumber, 0.0);
            passengerRatios.put(floorNumber, floorUserDistribution);
            totalPassengerCount += floorUserDistribution;
        }

        for (Map.Entry<Integer, Double> entry : passengerRatios.entrySet()) {
            int floorNumber = entry.getKey();
            double floorUserDistribution = entry.getValue();
            double ratio = (floorUserDistribution / totalPassengerCount) * 100;
            System.out.println("Floor " + floorNumber + " passenger ratio: " + ratio + "%");
        }
    }

    public void reset() {
        operating_floor.clear();
        // Reset any other state as necessary
    }





}
