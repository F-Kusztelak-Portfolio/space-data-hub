package com.fkusztel.space.data.hub.spacedatahub.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mission_name")
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
