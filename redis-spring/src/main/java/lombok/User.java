package lombok;


import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor // <--- THIS is it
@ToString
public class User implements Serializable {

    private Long id; // will be set when persisting

    private String firstName;
    private String lastName;
    private int age;

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }


}