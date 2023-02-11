package sn.sastrans.backofficev2.trace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.models.DetailAccident;

import java.util.Date;
import java.util.List;

@Service
public interface DetailAccidentService {
    DetailAccident saveDetailAccident(DetailAccident detailAccident);
    List<DetailAccident> getAllDetailAccident();
    List<DetailAccident> getDetailAccidentByEventId(Integer id);

    Page<DetailAccident> findPage(int pageNumber);
    Page<DetailAccident> getAllDetailAccidentWithSort(String keyword, Date dateDebut, Date dateFin, String field, String direction, int pageNumber) ;

    Page<DetailAccident> getDetailAccidentByKeyword(String keyword, Pageable pageable);
    DetailAccident getDetailAccidentById(int id);
    void deleteDetailAccident(Integer id);
}
