package com.example.FootballMatches.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "team")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String name;
    private List<String> footballers;
    private String coach;
    private String league;

    @JsonIgnore
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    List<Match> matches;
}
