package sn.sastrans.backofficev2.trace.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.trace.dto.RemorquageDto;
import sn.sastrans.backofficev2.trace.dto.RemorquageDto;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import java.util.List;
@Mapper(componentModel = "spring" ,uses={EvenementMapper.class})
public interface RemorquageMapper extends EntityMapper<RemorquageDto, Remorquage> {

    RemorquageMapper INSTANCE = Mappers.getMapper(RemorquageMapper.class);

    @Mapping(target = "dureeIntervention", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatDurationToString(remorquage.getHeureArriveeROM(), remorquage.getHeureRomorque()))")
    @Mapping(source = "evenement.localisation",target="localisation")
    RemorquageDto toDto(Remorquage remorquage);

    @InheritInverseConfiguration
    @Mapping(target = "evenement", ignore = true )
    Remorquage toEntity(RemorquageDto remorquageDto);

    List<RemorquageDto> toDto(List<Remorquage> remorquages);
    List<Remorquage> toEntity(List<RemorquageDto> remorquageDtos);


//    default Remorquage fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Remorquage Remorquage = new Remorquage();
//        Remorquage.setId(id);
//        return  Remorquage;
//    }
}