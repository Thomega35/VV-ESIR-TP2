package src;

public class TestClass {
    private int age;
    private String name;
    
    public String getName() { return name; }

    public boolean isJohn() {
        return age == 17 && name.equals("John");
    }

    public void main(String[] args) {
        System.out.println("Hello World!");
    }
}