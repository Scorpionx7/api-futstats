package br.com.futstats.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "competitions")
public class Competition {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID do campeonato na API externa
    @Column(name = "api_football_id", nullable = false, unique = true)
    private Integer apiFootballId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    private String logo;
}
