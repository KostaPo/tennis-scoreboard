package ru.kostapo.tennisscoreboard.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Matches")
public class Match {

    private static final String SEQ_NAME = "match_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player1", referencedColumnName = "id", nullable = false)
    private Player player1;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "player2", referencedColumnName = "id", nullable = false)
    private Player player2;

    @ManyToOne (fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "winner", referencedColumnName = "id", nullable = false)
    private Player winner;
}
