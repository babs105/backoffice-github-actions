package sn.sastrans.backofficev2.trace.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.trace.dto.IntrusionDto;
import sn.sastrans.backofficev2.trace.models.Intrusion;

import java.util.List;


@Mapper(componentModel = "spring")
public interface IntrusionMapper extends EntityMapper<IntrusionDto, Intrusion>{

    IntrusionMapper INSTANCE = Mappers.getMapper(IntrusionMapper.class);

//    @Mapping(source = "intrusion.causeEvent",target="cause")
//    @Mapping(target = "longBalisage", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.distanceBetweenPka(intrusion.getPkDebutBalisage(), intrusion.getPkFinBalisage()))")
//     @Mapping(target = "pkdebut", expression = "java((intrusion.getPkdebut()==null || intrusion.getPkdebut().isEmpty()) ? \"NON\":intrusion.getPkdebut())")
//     @Mapping(target = "pkfin", expression = "java((intrusion.getPkfin()==null || intrusion.getPkfin().isEmpty()) ? \"NON\":intrusion.getPkfin())")
//     @Mapping(target = "sens", expression = "java((intrusion.getSens()==null || intrusion.getSens().isEmpty()) ? \"NON\":intrusion.getSens())")
//     @Mapping(target = "voie", expression = "java((intrusion.getVoie()==null || intrusion.getVoie().isEmpty()) ? \"NON\":intrusion.getVoie())")
//     @Mapping(target = "gare", expression = "java((intrusion.getGare()==null || intrusion.getGare().isEmpty()) ? \"NON\":intrusion.getGare())")
//     @Mapping(target = "voie", expression = "java((intrusion.getVoie()==null || intrusion.getVoie().isEmpty()) ? \"NON\":intrusion.getVoie())")
//   @Mapping(target = "datePose", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(intrusion.getDatePose()))")
//   @Mapping(target = "dateDepose", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(intrusion.getDateDepose()))")
    IntrusionDto toDto(Intrusion intrusion);

    @InheritInverseConfiguration
    Intrusion toEntity(IntrusionDto intrusionDto);

    List<IntrusionDto> toDto(List<Intrusion> intrusions);
    List<Intrusion> toEntity(List<IntrusionDto> intrusionDtos);


//    default intrusion fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        intrusion intrusion = new intrusion();
//        intrusion.setId(id);
//        return  intrusion;
//    }
}
