package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.sastrans.backofficev2.trace.dto.RomSearchDto;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import java.util.List;

public interface RemorquageRepositoryCustom  {
    Page<Remorquage> searchRemorquage(RomSearchDto critere,int page,int size);
}


// between ?1 and ?2  OR e.dateEvent >= ?2 AND e.dateEvent <= ?3