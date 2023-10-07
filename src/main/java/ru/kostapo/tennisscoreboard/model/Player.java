package ru.kostapo.tennisscoreboard.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Players", indexes = {@Index(name = "idx_name", columnList = "name")})
public class Player {

    private static final String SEQ_NAME = "player_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 16)
    private String name;
}
