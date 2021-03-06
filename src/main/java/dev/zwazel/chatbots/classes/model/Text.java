package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.zwazel.chatbots.config.Constants;
import jakarta.persistence.*;
import jakarta.ws.rs.FormParam;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Text class
 *
 * @author Zwazel
 * @since 0.2
 */
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@JsonIgnoreProperties({"chatbotUnknownTexts", "questionAnswerQuestions", "questionAnswerAnswers"})
public class Text {
    /**
     * Text ID
     *
     * @since 0.2
     */
    @Id
    @Column(name = "id", nullable = false, length = Constants.UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @dev.zwazel.chatbots.util.annotation.UUID
    private String id = UUID.randomUUID().toString();

    /**
     * Text
     *
     * @since 0.2
     */
    @NonNull
    @Column(name = "text", nullable = false, length = Constants.MAX_TEXT_LENGTH)
    @dev.zwazel.chatbots.util.annotation.Text
    @FormParam("text")
    private String text;

    /**
     * All the unknown texts of a Chatbot.
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "unknownText", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @Builder.Default
    private Set<ChatbotUnknownTexts> chatbotUnknownTexts = new LinkedHashSet<>();

    /**
     * Questions this text belongs to
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "question", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private Set<QuestionAnswerQuestion> questionAnswerQuestions = new LinkedHashSet<>();

    /**
     * Answers this text belongs to
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "answer", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private Set<QuestionAnswerAnswer> questionAnswerAnswers = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Text text = (Text) o;
        return id != null && Objects.equals(id, text.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}