package sn.sastrans.backofficev2.report.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.parameters.dto.VehicleDto;
import sn.sastrans.backofficev2.parameters.mappers.EntityMapper;
import sn.sastrans.backofficev2.parameters.models.Vehicle;
import sn.sastrans.backofficev2.report.dtos.EvenementReportDto;
import sn.sastrans.backofficev2.trace.models.Evenement;


import java.util.List;


@Mapper(componentModel = "spring")
public interface EvenementReportMapper extends EntityMapper<EvenementReportDto, Evenement> {

    EvenementReportMapper INSTANCE = Mappers.getMapper(EvenementReportMapper.class);

    @Mapping(target = "heureAppelOPC", expression = "java(evenement.getHeureAppelOPC().isEmpty() ? \"NON\":evenement.getHeureAppelOPC())")
    @Mapping(target = "heureAppelPAT", expression = "java(evenement.getHeureAppelPAT().isEmpty() ? \"NON\":evenement.getHeureAppelPAT())")
    @Mapping(target = "heureArriveePAT", expression = "java(evenement.getHeureArriveePAT().isEmpty() ? \"NON\":evenement.getHeureArriveePAT())")
    @Mapping(target = "nomPAT", expression = "java(evenement.getNomPAT().isEmpty() ? \"NON\":evenement.getNomPAT())")
    @Mapping(target = "dateheurePoseBalise", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(evenement.getDateheurePoseBalise()))")
    @Mapping(target = "dateheureDeposeBalise", expression = "java(sn.sastrans.backofficev2.trace.utils.TraceUtil.formatLocalDateTimeToString(evenement.getDateheureDeposeBalise()))")
    @Mapping(target = "typeBalisage", expression = "java(evenement.getTypeBalisage().isEmpty() ? \"NON\":evenement.getTypeBalisage())")
    @Mapping(target = "pkDebutBalisage", expression = "java(evenement.getPkDebutBalisage().isEmpty() ? \"NON\":evenement.getPkDebutBalisage())")
    @Mapping(target = "pkFinBalisage", expression = "java(evenement.getPkFinBalisage().isEmpty() ? \"NON\":evenement.getPkFinBalisage())")
    @Mapping(source = "evenement.heureDebutEvent",target="heureDebutEvent")
    EvenementReportDto toDto(Evenement evenement);

    @InheritInverseConfiguration
    Evenement toEntity(EvenementReportDto evenementReportDto);

    List<EvenementReportDto> toDto(List<Evenement> evenements);
    List<Evenement> toEntity(List<EvenementReportDto> evenementReportDtos);


//    default Vehicle fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Vehicle Vehicle = new Vehicle();
//        Vehicle.setId(id);
//        return  Vehicle;
//    }
}
