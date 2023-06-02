public class EfficiencyCalculation {

    public static void main(String[] args) {
        Building building = new Building();
        double maxTotalAllocation = 0;
        int overThresholdCount = 0;
        double threshold = 0; // Set the threshold for total resource allocation

        while (overThresholdCount < 100) {
            Elevator elevator1 = new Elevator(building);
            Elevator elevator2 = new Elevator(building);
            Elevator elevator3 = new Elevator(building);

            building.resetResourceDistribution();
            elevator1.reset();
            elevator2.reset();
            elevator3.reset();

            elevator1.setOperating_floor(new int[]{2, 3, 4, 5});
            elevator1.setResource();

            elevator2.setOperating_floor(new int[]{2, 3, 4, 5, 6, 7, 8});
            elevator2.setResource();

            elevator3.setOperating_floor(new int[]{2, 3, 4, 5, 6, 7, 8});
            elevator3.setResource();

            building.distributeResources(elevator1, elevator2, elevator3);

            float totalAllocation = building.calculateTotalResourceAllocation();
            if (totalAllocation >= threshold) {
                overThresholdCount++;
                if (totalAllocation > maxTotalAllocation) {
                    maxTotalAllocation = totalAllocation;
                }
                System.out.println("Over threshold count: " + overThresholdCount);
                System.out.println("Current total resource allocation: " + totalAllocation);
                System.out.println("Elevator 1 operating floors: " + elevator1.getOperating_floor());
                System.out.println("Elevator 2 operating floors: " + elevator2.getOperating_floor());
                System.out.println("Elevator 3 operating floors: " + elevator3.getOperating_floor());
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
        System.out.println("Maximum total resource allocation during the 100 iterations: " + maxTotalAllocation);
    }
}