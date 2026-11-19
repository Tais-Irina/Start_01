import lombok.Getter;

import java.util.List;

@Getter
public class Student {
    String name;
    String secondName;
    String studyGroup;
    Integer streamGroup;
    List<Integer> marksList;
    List<String> passedBlocks;

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }
}
