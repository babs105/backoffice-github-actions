package sn.sastrans.backofficev2.trace.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.trace.dto.EvenementDto;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.List;


@Mapper(componentModel = "spring")
public interface EvenementMapper extends EntityMapper<EvenementDto, Evenement>{

    EvenementMapper INSTANCE = Mappers.getMapper(EvenementMapper.class);

//    @Mapping(source = "evenement.causeEvent",target="cause")
    @Mapping(target = "longBalisage", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.distanceBetweenPka(evenement.getPkDebutBalisage(), evenement.getPkFinBalisage()))")
    EvenementDto toDto(Evenement evenement);

    @InheritInverseConfiguration
    Evenement toEntity(EvenementDto evenementDto);

    List<EvenementDto> toDto(List<Evenement> evenements);
    List<Evenement> toEntity(List<EvenementDto> evenementDtos);


//    default Evenement fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Evenement evenement = new Evenement();
//        evenement.setId(id);
//        return  evenement;
//    }
}
