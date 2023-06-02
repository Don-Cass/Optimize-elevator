

public class Damhun {

    public Damhun() {
    }

    public static void main(String[] args) {
        Building building = new Building();
        double difference;

        // Track consecutive times the difference is under the threshold
        int underThresholdCount = 0;
        double minDifference = Double.MAX_VALUE;

        while (true) {
            Elevator elevator1 = new Elevator(building);
            Elevator elevator2 = new Elevator(building);
            Elevator elevator3 = new Elevator(building);

            building.resetResourceDistribution();
            elevator1.reset();
            elevator2.reset();
            elevator3.reset();

            elevator1.setOperating_floor(new int[]{2, 3, 4, 5, 6, 7, 8});
            elevator1.setResource();

            elevator2.setOperating_floor_Random();
            elevator2.setResource();

            elevator3.setOperating_floor_Random();
            elevator3.setResource();

            building.distributeResources(elevator1, elevator2, elevator3);

            difference = building.calculateDistributionDifference();

            if (difference < 100) {
                underThresholdCount++;
                if (difference < minDifference) {
                    minDifference = difference;
                }
                System.out.println("Under threshold count: " + underThresholdCount);
                System.out.println("Current difference: " + difference);
                elevator1.printOperatingFloors();
                elevator2.printOperatingFloors();
                elevator3.printOperatingFloors();
                elevator1.printPassengerRatio();
                elevator2.printPassengerRatio();
                elevator3.printPassengerRatio();
            }


            if (underThresholdCount >= 100) {
                break;
            }
        }

        System.out.println("Final resource distribution:");
        Elevator elevator1 = new Elevator(building);
        Elevator elevator2 = new Elevator(building);
        Elevator elevator3 = new Elevator(building);
        elevator1.printOperatingFloors();
        elevator2.printOperatingFloors();
        elevator3.printOperatingFloors();

        building.printResourceRatioPerFloor();
        building.printResourcesPerFloor();
        System.out.println("Final distribution difference: " + difference);
        System.out.println("Minimum difference during the 100 iterations: " + minDifference);
    }
}
