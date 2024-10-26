package com.work.webhook.repository;

import com.work.webhook.model.ApplicationDO;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationDO, Long> {

    @Modifying
    @Transactional
    @Query("update ApplicationDO d set d.online = true where d.id = :id")
    int setDestinationOnline(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update ApplicationDO d set d.online = false where d.id = :id")
    int setDestinationOffline(@Param("id") Long id);

}
