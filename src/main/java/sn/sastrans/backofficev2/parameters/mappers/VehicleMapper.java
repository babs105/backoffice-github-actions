package sn.sastrans.backofficev2.parameters.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.parameters.dto.VehicleDto;
import sn.sastrans.backofficev2.parameters.models.Vehicle;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VehicleMapper extends EntityMapper<VehicleDto, Vehicle> {

    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);


    VehicleDto toDto(Vehicle vehicle);

    @InheritInverseConfiguration
    Vehicle toEntity(VehicleDto vehicleDto);

    List<VehicleDto> toDto(List<Vehicle> vehicles);
    List<Vehicle> toEntity(List<VehicleDto> vehicleDtos);


//    default Vehicle fromId(Integer id){
//        if(id==null){
//            return null;
//        }
//        Vehicle Vehicle = new Vehicle();
//        Vehicle.setId(id);
//        return  Vehicle;
//    }
}
