package sn.sastrans.backofficev2.carburant.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.carburant.dto.RavitaillementDto;
import sn.sastrans.backofficev2.carburant.models.Ravitaillement;
import sn.sastrans.backofficev2.parameters.mappers.VehicleMapper;
import sn.sastrans.backofficev2.trace.mappers.EvenementMapper;

import java.util.List;


@Mapper(componentModel = "spring",uses={CuveMapper.class, VehicleMapper.class})
public interface RavitaillementMapper extends EntityMapper<RavitaillementDto, Ravitaillement> {

    RavitaillementMapper INSTANCE = Mappers.getMapper(RavitaillementMapper.class);

//    @Mapping(source = "vehicle.matricule",target="matricule")
   @Mapping(source = "vehicle.vehicleaffectationid",target="vehicleaffectationid")
    RavitaillementDto toDto(Ravitaillement ravitaillement);

    @InheritInverseConfiguration
    @Mapping(target = "vehicle", ignore = true )
    @Mapping(target = "cuve", ignore = true )
    Ravitaillement toEntity(RavitaillementDto ravitaillementDto);

    List<RavitaillementDto> toDto(List<Ravitaillement> ravitaillements);
    List<Ravitaillement> toEntity(List<RavitaillementDto> ravitaillementDtos);


//    default Ravitaillement fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Ravitaillement Ravitaillement = new Ravitaillement();
//        Ravitaillement.setId(id);
//        return  Ravitaillement;
//    }
}
