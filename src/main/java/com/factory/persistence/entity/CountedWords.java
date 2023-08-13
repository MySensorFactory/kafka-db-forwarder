package com.factory.persistence.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "counted_words", schema = "factory_data")
@Data
public class CountedWords {

    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @Column(name = "letters_count")
    private Long lettersCount;
}
