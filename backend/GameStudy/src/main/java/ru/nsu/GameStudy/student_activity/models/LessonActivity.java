package ru.nsu.GameStudy.student_activity.models;

import jakarta.persistence.*;
import lombok.*;
import ru.nsu.GameStudy.courses.models.Lesson;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lesson_results", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lesson_id", "student_id"})
})
public class LessonActivity extends ActivityRecord {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    public ActivityType getActivity() {
        return ActivityType.LESSON_COMPLETE;
    }
}
