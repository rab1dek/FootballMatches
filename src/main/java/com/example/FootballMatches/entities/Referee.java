package com.example.FootballMatches.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
public class Referee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refereeId;
    private String name;
    private String surname;
    private String league;

    @JsonIgnore
    @OneToMany(mappedBy = "referee", fetch = FetchType.EAGER)
    List<Match> matches;
}
