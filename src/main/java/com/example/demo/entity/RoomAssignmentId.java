package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoomAssignmentId implements Serializable {

    private Long roomId;
    private Long studentId;

    public RoomAssignmentId() {
    }

    public RoomAssignmentId(Long roomId, Long studentId) {
        this.roomId = roomId;
        this.studentId = studentId;
    }

    // equals() và hashCode() để JPA so sánh PK
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomAssignmentId)) return false;
        RoomAssignmentId that = (RoomAssignmentId) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, studentId);
    }

    // getters, setters ...
}
