package com.fkusztel.space.data.hub.spacedatahub.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
  @Enumerated(EnumType.STRING)
  private ImageType imageType;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "byMission")
  private Product product;
}
