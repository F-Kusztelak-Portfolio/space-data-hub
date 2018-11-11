package com.fkusztel.space.data.hub.spacedatahub.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Filip.Kusztelak
 */
@Entity(name = "Mission")
@Table(name = "mission")
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mission implements Serializable {

    @Id
    @Column(name = "mission_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 50)
    @Column(name = "mission_name", unique = true)
    private String name;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToOne(mappedBy = "byMission", optional = false)
    private Product product;
}
