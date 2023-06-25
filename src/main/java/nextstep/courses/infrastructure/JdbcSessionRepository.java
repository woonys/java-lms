package nextstep.courses.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import nextstep.courses.domain.CardinalNumber;
import nextstep.courses.domain.Enrollment;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.MaximumClassSize;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionType;
import nextstep.users.domain.UserRepository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;
    private final UserRepository userRepository;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into session"
                     + " (course_id, cardinal_number, session_type, session_status, start_date, end_date, created_at, maxinum_class_size, image)"
                     + " values(?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                                   session.getCourseId(),
                                   session.getCardinalNumber(),
                                   session.getSessionType(),
                                   session.getSessionStatus(),
                                   session.getStartDate(),
                                   session.getEndDate(),
                                   session.getCreatedAt(),
                                   session.getMaximumClassSize(),
                                   session.getImageUrl());
    }

    @Override
    public Session findById(Long sessionId) {
        String sql = "SELECT id, course_id, cardinal_number, session_type, session_status, start_date, end_date, maximum_class_size"
                     + " FROM session"
                     + " WHERE id = ?";
        Session session = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long courseId = rs.getLong("course_id");
            int cardinalNumber = rs.getInt("cardinal_number");
            String sessionType = rs.getString("session_type");
            String sessionStatus = rs.getString("session_status");
            LocalDate startDate = rs.getDate("start_date").toLocalDate();
            LocalDate endDate = rs.getDate("end_date").toLocalDate();
            LocalDateTime createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
            LocalDateTime updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));
            int maximumClassSize = rs.getInt("maximum_class_size");
            String image = rs.getString("image");
            Enrollment enrollment = new Enrollment(userRepository.findAllUsersBySessionId(sessionId));

            return new Session(
                id,
                courseId,
                new CardinalNumber(cardinalNumber),
                SessionType.valueOf(sessionType),
                SessionStatus.valueOf(sessionStatus),
                startDate,
                endDate,
                createdAt,
                updatedAt,
                enrollment,
                new MaximumClassSize(maximumClassSize),
                new Image(image));
        });
        return session;
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
