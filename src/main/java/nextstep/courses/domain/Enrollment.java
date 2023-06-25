package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

import nextstep.users.domain.NsUser;

public class Enrollment {
    private List<NsUser> enrollment;

    public Enrollment() {
        this.enrollment = new ArrayList<>();
    }

    public void enroll(NsUser user) {
        enrollment.add(user);
    }

    public boolean isFull(int size) {
        return enrollment.size() >= size;
    }
}
