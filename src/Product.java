public class Product {
    public static final int RECORD_SIZE = 6 + 35 + 75 + 8; // ID (6 bytes) + Name (35 bytes) + Description (75 bytes) + Cost (8 bytes)
    private String name;
    private String description;
    private String ID;
    private double cost;

    public Product(String ID, String name, String description, double cost) {
        this.name = truncateString(name, 35);
        this.description = truncateString(description, 75);
        this.ID = truncateString(ID, 6);
        this.cost = cost;

    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = truncateString(description, 75);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = truncateString(name, 35);
    }

    public String getNameAndDesc() {
        return this.name + " , " + this.description;
    }

    public String toProRecord() {
        return this.ID + ", " + this.name + ", " + this.description + " ," + this.cost;
    }

    private String truncateString(String input, int maxLength) {
        if (input.length() <= maxLength) {
            return input;
        } else {
            return input.substring(0, maxLength);
        }
    }
}
