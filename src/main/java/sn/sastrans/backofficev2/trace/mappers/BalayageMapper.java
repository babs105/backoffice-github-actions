package sn.sastrans.backofficev2.trace.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.trace.dto.BalayageDto;
import sn.sastrans.backofficev2.trace.models.Balayage;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BalayageMapper extends EntityMapper<BalayageDto, Balayage>{

    BalayageMapper INSTANCE = Mappers.getMapper(BalayageMapper.class);

//    @Mapping(source = "Balayage.causeEvent",target="cause")
//    @Mapping(target = "longBalisage", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.distanceBetweenPka(Balayage.getPkDebutBalisage(), Balayage.getPkFinBalisage()))")
//    @Mapping(target = "nomPAT", expression = "java(Balayage.getNomPAT().isEmpty() ? \"NON\":Balayage.getNomPAT())")
//   @Mapping(target = "datePose", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(balayage.getDatePose()))")
//   @Mapping(target = "dateDepose", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(balayage.getDateDepose()))")
    BalayageDto toDto(Balayage balayage);

    @InheritInverseConfiguration
    Balayage toEntity(BalayageDto balayageDto);

    List<BalayageDto> toDto(List<Balayage> balayages);
    List<Balayage> toEntity(List<BalayageDto> balayageDtos);


//    default Balayage fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Balayage Balayage = new Balayage();
//        Balayage.setId(id);
//        return  Balayage;
//    }
}
