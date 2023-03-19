package sn.sastrans.backofficev2.trace.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.trace.dto.NettoyageDto;
import sn.sastrans.backofficev2.trace.models.Nettoyage;

import java.util.List;


@Mapper(componentModel = "spring")
public interface NettoyageMapper extends EntityMapper<NettoyageDto, Nettoyage>{

    NettoyageMapper INSTANCE = Mappers.getMapper(NettoyageMapper.class);

//    @Mapping(source = "Nettoyage.causeEvent",target="cause")
//    @Mapping(target = "longBalisage", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.distanceBetweenPka(Nettoyage.getPkDebutBalisage(), Nettoyage.getPkFinBalisage()))")
//    @Mapping(target = "nomPAT", expression = "java(Nettoyage.getNomPAT().isEmpty() ? \"NON\":Nettoyage.getNomPAT())")
//   @Mapping(target = "datePose", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(Nettoyage.getDatePose()))")
//   @Mapping(target = "dateDepose", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(Nettoyage.getDateDepose()))")
    NettoyageDto toDto(Nettoyage nettoyage);

    @InheritInverseConfiguration
    Nettoyage toEntity(NettoyageDto nettoyageDto);

    List<NettoyageDto> toDto(List<Nettoyage> nettoyages);
    List<Nettoyage> toEntity(List<NettoyageDto> nettoyageDtos);


//    default Nettoyage fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Nettoyage Nettoyage = new Nettoyage();
//        Nettoyage.setId(id);
//        return  Nettoyage;
//    }
}
