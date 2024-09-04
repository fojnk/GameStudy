package ru.nsu.GameStudy.authentication.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "details", columnDefinition = "text")
    private String details;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "recipients_notifications",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"notification_id", "recipient_id"})},
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id"))
    @Fetch(FetchMode.JOIN)
    private List<User> recipients;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "confirmed_notifications",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"notification_id", "recipient_id"})},
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id"))
    @Fetch(FetchMode.JOIN)
    private List<User> recipientsConfirmed;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private User sender;
}
