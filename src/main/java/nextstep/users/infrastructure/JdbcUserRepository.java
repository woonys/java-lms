package nextstep.users.infrastructure;

import nextstep.courses.domain.Enrollment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("userRepository")
public class JdbcUserRepository implements UserRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcUserRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<NsUser> findByUserId(String userId) {
        String sql = "select id, user_id, password, name, email, created_at, updated_at from ns_user where user_id = ?";
        RowMapper<NsUser> rowMapper = (rs, rowNum) -> new NsUser(
            rs.getLong(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getString(5),
            toLocalDateTime(rs.getTimestamp(6)),
            toLocalDateTime(rs.getTimestamp(7)));
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, userId));
    }

    @Override
    public List<NsUser> findAllUsersBySessionId(Long sessionId) {
        String sql = "SELECT * FROM ns_user WHERE session_id = ?";
        return jdbcTemplate.query(sql, new UserMapper(), sessionId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    private static final class UserMapper implements RowMapper<NsUser> {
        public NsUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            NsUser user = new NsUser(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getTimestamp(6).toLocalDateTime(),
                rs.getTimestamp(7).toLocalDateTime());
            return user;
        }
    }
}
