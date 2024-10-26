package com.work.webhook.repository;

import com.work.webhook.model.ApplicationDO;
import com.work.webhook.model.MessageDO;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageDO, Long> {

    List<MessageDO> findAllByApplicationOrderByIdAsc(ApplicationDO destination);
}
