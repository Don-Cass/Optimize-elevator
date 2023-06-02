public class Floor {
    private int floorNumber;
    private float totalResource;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void addResource(double resource) {
        totalResource += resource;
    }

    public float getTotalResource() {
        return totalResource;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
    public void setTotalResource(float totalResource) {
        this.totalResource = totalResource;
    }
}