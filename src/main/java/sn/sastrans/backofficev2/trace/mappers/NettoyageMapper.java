package sn.sastrans.backofficev2.trace.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.trace.dto.NettoyageDto;
import sn.sastrans.backofficev2.trace.models.Nettoyage;

import java.util.List;


@Mapper(componentModel = "spring")
public interface NettoyageMapper extends EntityMapper<NettoyageDto, Nettoyage>{

    NettoyageMapper INSTANCE = Mappers.getMapper(NettoyageMapper.class);

//    @Mapping(source = "Nettoyage.causeEvent",target="cause")
//    @Mapping(target = "longBalisage", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.distanceBetweenPka(Nettoyage.getPkDebutBalisage(), Nettoyage.getPkFinBalisage()))")
     @Mapping(target = "pkdebut", expression = "java((nettoyage.getPkdebut()==null || nettoyage.getPkdebut().isEmpty()) ? \"NON\":nettoyage.getPkdebut())")
     @Mapping(target = "pkfin", expression = "java((nettoyage.getPkfin()==null || nettoyage.getPkfin().isEmpty()) ? \"NON\":nettoyage.getPkfin())")
     @Mapping(target = "sens", expression = "java((nettoyage.getSens()==null || nettoyage.getSens().isEmpty()) ? \"NON\":nettoyage.getSens())")
     @Mapping(target = "voie", expression = "java((nettoyage.getVoie()==null || nettoyage.getVoie().isEmpty()) ? \"NON\":nettoyage.getVoie())")
     @Mapping(target = "gare", expression = "java((nettoyage.getGare()==null || nettoyage.getGare().isEmpty()) ? \"NON\":nettoyage.getGare())")
//     @Mapping(target = "voie", expression = "java((nettoyage.getVoie()==null || nettoyage.getVoie().isEmpty()) ? \"NON\":nettoyage.getVoie())")
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
