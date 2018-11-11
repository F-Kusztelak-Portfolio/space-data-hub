package com.fkusztel.space.data.hub.spacedatahub.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Filip.Kusztelak
 */
public interface MissionRepository extends CrudRepository<Mission, Long>  {

    void deleteById(Long id);
}
