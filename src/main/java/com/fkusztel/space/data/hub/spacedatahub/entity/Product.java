package com.fkusztel.space.data.hub.spacedatahub.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Filip.Kusztelak
 */
@Entity(name = "Product")
@Table(name = "product")
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mission_name")
    private Mission byMission;

    @ElementCollection
    @Column(name = "footprint")
    private List<String> footprint;

    @Column(name = "price")
    private Double price;

    @Column(name = "name_mission")
    private String nameMission;

    @Column(name = "product_url")
    private String url;

    @Column(name = "purchased")
    private Boolean purchased;
}
