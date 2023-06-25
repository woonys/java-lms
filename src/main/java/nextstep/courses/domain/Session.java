package nextstep.courses.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import nextstep.users.domain.NsUser;

public class Session {
    private Long id;
    private Long courseId;
    private CardinalNumber cardinalNumber;
    private SessionType sessionType;
    private SessionStatus sessionStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Enrollment enrollment;
    private MaximumClassSize maximumClassSize;
    private Image image;

    public Session(Long id,
                   Long courseId,
                   CardinalNumber cardinalNumber,
                   SessionType sessionType,
                   SessionStatus sessionStatus,
                   LocalDate startDate,
                   LocalDate endDate,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   Enrollment enrollment,
                   MaximumClassSize maximumClassSize,
                   Image image
                   ) {
        new Session(id, courseId, cardinalNumber, sessionType, sessionStatus, startDate, endDate, createdAt, updatedAt, enrollment, maximumClassSize, image);
    }

    public Session(SessionStatus sessionStatus, MaximumClassSize maximumClassSize) {
        this.sessionStatus = sessionStatus;
        this.maximumClassSize = maximumClassSize;
        this.enrollment = new Enrollment();
    }

    public Session() {
        this.image = new Image();
    }

    public Session(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public Session(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public Session(int cardinalNumber) {
        this.cardinalNumber = new CardinalNumber(cardinalNumber);
    }
    public Session(CardinalNumber cardinalNumber, LocalDate startDate, LocalDate endDate) {
        this.cardinalNumber = cardinalNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getCourseId() {
        return courseId;
    }

    public int getCardinalNumber() {
        return cardinalNumber.cardinalNumber();
    }

    public String getSessionStatus() {
        return sessionStatus.name();
    }

    public String getSessionType() {
        return sessionType.name();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getMaximumClassSize() {
        return maximumClassSize.maxSize();
    }

    public String getImageUrl() {
        return image.getUrl();
    }

    boolean isStartDateSame(LocalDate localDate) {
        return startDate == localDate;
    }

    boolean isEndDateSame(LocalDate localDate) {
        return endDate == localDate;
    }

    public void updateImageWithUrl(String imgUrl) {
        image.updateUrl(imgUrl);
    }

    public boolean isSameCoverImage(String imgUrl) {
        return image.isSameImage(imgUrl);
    }

    public boolean isFree() {
        return sessionType.isFree();
    }

    public boolean isReady() {
        return sessionStatus.isReady();
    }

    public boolean isOpen() {
        return sessionStatus.isOpen();
    }

    public boolean isClosed() {
        return sessionStatus.isClosed();
    }

    public boolean isEnrollPossible() {
        return isOpen() && !isMoreThanMaximumClassSize();
    }

    private boolean isMoreThanMaximumClassSize() {
        return enrollment.isFull(maximumClassSize.maxSize());
    }

    public void enroll(NsUser user) {
        if (!isEnrollPossible()) {
            throw new IllegalStateException("수강 인원이 초과되었습니다.");
        }
        enrollment.enroll(user);
    }

    public Object getCreatedAt() {
        return createdAt;
    }
}
