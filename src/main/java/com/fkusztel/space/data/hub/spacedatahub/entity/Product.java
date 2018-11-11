package com.fkusztel.space.data.hub.spacedatahub.entity;

import com.fkusztel.space.data.hub.spacedatahub.dto.FootprintDto;
import lombok.*;
import org.springframework.data.geo.Polygon;

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

    @Column(name = "product_url")
    private String url;
}
