import java.util.*;

public class Building {
    private Map<Integer, Floor> floors = new HashMap<>();
    private Map<Integer, Double> userDistribution = new HashMap<>();

    public Building() {
        // 사용자 분포 초기화
        userDistribution.put(2, 10.79);
        userDistribution.put(3, 9.62);
        userDistribution.put(4, 5.00);
        userDistribution.put(5, 5.97);
        userDistribution.put(6, 4.62);
        userDistribution.put(7, 36.02);
        userDistribution.put(8, 27.96);
    }

    public Floor getFloor(int floorNumber) {
        return floors.computeIfAbsent(floorNumber, Floor::new);
    }

    public void distributeResources(Elevator... elevators) {
        for (Elevator elevator : elevators) {
            elevator.distributeResourceToFloors();
        }
    }

    public void printResourcesPerFloor() {
        for (Map.Entry<Integer, Floor> entry : floors.entrySet()) {
            System.out.println("Floor " + entry.getKey() + ": " + entry.getValue().getTotalResource());
        }
    }

    public void printResourceRatioPerFloor() {
        // Total resources calculation
        float totalResources = 0f;
        for (Floor floor : floors.values()) {
            totalResources += floor.getTotalResource();
        }

        // Each floor's resource ratio calculation and printing
        for (Floor floor : floors.values()) {
            double ratio = floor.getTotalResource() / totalResources * 100;  // ratio calculation
            System.out.println("Floor " + floor.getFloorNumber() + ": " + ratio + "%");  // print
        }
    }

    public double calculateDistributionDifference() {
        double totalDifferenceRatio = 0.0;

        // 총 자원 계산
        float totalResources = 0f;
        for (Floor floor : floors.values()) {
            totalResources += floor.getTotalResource();
        }

        for (Floor floor : floors.values()) {
            // 각 층의 자원 분포 계산
            double resourceRatio = floor.getTotalResource() / totalResources * 100;

            // 사용자 분포에서 해당 층의 비율을 가져옴
            double userRatio = userDistribution.get(floor.getFloorNumber());

            // 비율 차이의 비율 계산
            double differenceRatio = Math.abs(resourceRatio - userRatio) / userRatio;

            totalDifferenceRatio += differenceRatio;
        }

        return totalDifferenceRatio / floors.size() * 100;
    }

    public void resetResourceDistribution() {
        for (Floor floor : floors.values()) {
            floor.setTotalResource(0);
        }
    }

    public void addResourceToFloor(int floorNumber, float resource) {
        Floor floor = floors.get(floorNumber);
        if (floor != null) {
            floor.addResource(resource);
        }
    }

    public Map<Integer, Double> getUserDistribution() {
        return userDistribution;
    }


    public float calculateTotalResourceAllocation() {
        float totalAllocation = 0f;
        for (Floor floor : floors.values()) {
            totalAllocation += floor.getTotalResource();
        }
        return totalAllocation;
    }

}