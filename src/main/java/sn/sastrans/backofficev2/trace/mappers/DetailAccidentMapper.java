package sn.sastrans.backofficev2.trace.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.trace.dto.DetailAccidentDto;
import sn.sastrans.backofficev2.trace.models.DetailAccident;

import java.util.List;

@Mapper(componentModel = "spring" ,uses= EvenementMapper.class)
public interface DetailAccidentMapper extends EntityMapper<DetailAccidentDto, DetailAccident>{

    DetailAccidentMapper INSTANCE = Mappers.getMapper(DetailAccidentMapper.class);


    @Mapping(source = "evenement.localisation",target="localisation")
    DetailAccidentDto toDto(DetailAccident detailAccident);

    @InheritInverseConfiguration
//    @Mapping(source = "localisation",target="evenement.localisation")
//    @Mapping(source = "eventid",target="evenement.id")
//    @Mapping(source = "deleted", target = "evenement.deleted", defaultValue = "false")
    @Mapping(target = "evenement", ignore = true )
    DetailAccident toEntity(DetailAccidentDto detailAccidentDto);

    List<DetailAccidentDto> toDto(List<DetailAccident> detailAccidents);
    List<DetailAccident> toEntity(List<DetailAccidentDto> detailAccidentDtos);


//    default DetailAccident fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        DetailAccident DetailAccident = new DetailAccident();
//        DetailAccident.setId(id);
//        return  DetailAccident;
//    }
}
