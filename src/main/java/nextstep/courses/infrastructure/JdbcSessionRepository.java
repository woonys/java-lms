package nextstep.courses.infrastructure;

import org.springframework.stereotype.Repository;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    @Override
    int save(Session session) {

    }

    @Override
    public Session findById(Long id) {
        return null;
    }
}
