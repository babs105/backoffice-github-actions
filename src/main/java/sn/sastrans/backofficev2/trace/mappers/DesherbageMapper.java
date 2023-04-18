package sn.sastrans.backofficev2.trace.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.trace.dto.DesherbageDto;
import sn.sastrans.backofficev2.trace.models.Desherbage;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DesherbageMapper extends EntityMapper<DesherbageDto, Desherbage>{

    DesherbageMapper INSTANCE = Mappers.getMapper(DesherbageMapper.class);

//    @Mapping(source = "Desherbage.causeEvent",target="cause")
//    @Mapping(target = "longBalisage", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.distanceBetweenPka(Desherbage.getPkDebutBalisage(), Desherbage.getPkFinBalisage()))")
//     @Mapping(target = "pkdebut", expression = "java((Desherbage.getPkdebut()==null || Desherbage.getPkdebut().isEmpty()) ? \"NON\":Desherbage.getPkdebut())")
//     @Mapping(target = "pkfin", expression = "java((Desherbage.getPkfin()==null || Desherbage.getPkfin().isEmpty()) ? \"NON\":Desherbage.getPkfin())")
//     @Mapping(target = "sens", expression = "java((Desherbage.getSens()==null || Desherbage.getSens().isEmpty()) ? \"NON\":Desherbage.getSens())")
//     @Mapping(target = "voie", expression = "java((Desherbage.getVoie()==null || Desherbage.getVoie().isEmpty()) ? \"NON\":Desherbage.getVoie())")
//     @Mapping(target = "gare", expression = "java((Desherbage.getGare()==null || Desherbage.getGare().isEmpty()) ? \"NON\":Desherbage.getGare())")
//     @Mapping(target = "voie", expression = "java((Desherbage.getVoie()==null || Desherbage.getVoie().isEmpty()) ? \"NON\":Desherbage.getVoie())")
//   @Mapping(target = "datePose", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(Desherbage.getDatePose()))")
    @Mapping(target = "pkDebut", expression = "java((desherbage.getPkDebut()==null || desherbage.getPkDebut().isEmpty()) ? \"NON\":desherbage.getPkDebut())")
    @Mapping(target = "pkFin", expression = "java((desherbage.getPkFin()==null || desherbage.getPkFin().isEmpty()) ? \"NON\":desherbage.getPkFin())")
    @Mapping(target = "lineaire", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.distanceBetweenPka(desherbage.getPkDebut(), desherbage.getPkFin()))")
    //   @Mapping(target = "dateDepose", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(Desherbage.getDateDepose()))")
    DesherbageDto toDto(Desherbage desherbage);

    @InheritInverseConfiguration
    Desherbage toEntity(DesherbageDto desherbageDto);

    List<DesherbageDto> toDto(List<Desherbage> desherbages);
    List<Desherbage> toEntity(List<DesherbageDto> desherbageDtos);


//    default Desherbage fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Desherbage Desherbage = new Desherbage();
//        Desherbage.setId(id);
//        return  Desherbage;
//    }
}
