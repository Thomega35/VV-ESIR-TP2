package src;

public class TestClass {
    private int age;
    private String name;
    
    public String getName() { return name; }

    public boolean isAdult() {
        return age > 17;
    }

    public void main(String[] args) {
        System.out.println("Hello World!");
    }
}