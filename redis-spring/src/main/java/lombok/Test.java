package lombok;

public class Test {
    public static void main(String[] args) {
        User user = new User("aaa", "bbb", 10);
        System.out.println(user.getFirstName());
    }
}
