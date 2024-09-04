package ru.nsu.GameStudy.student_activity.models;

import jakarta.persistence.*;
import lombok.*;
import ru.nsu.GameStudy.courses.models.Task;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_results", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"task_id", "student_id"})
})
public class TaskActivity extends ActivityRecord {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id", referencedColumnName = "id")
    private Task task;

    public ActivityType getActivity() {
        return ActivityType.TASK_COMPLETE;
    }
}
