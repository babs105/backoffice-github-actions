package sn.sastrans.backofficev2.parameters.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.parameters.dto.VehicleAffectationDto;
import sn.sastrans.backofficev2.parameters.models.Vehicle;
import sn.sastrans.backofficev2.parameters.models.VehicleAffectation;

import java.util.List;


@Mapper(componentModel = "spring")
public interface VehicleAffectationMapper extends EntityMapper<VehicleAffectationDto, VehicleAffectation> {

    VehicleAffectationMapper INSTANCE = Mappers.getMapper(VehicleAffectationMapper.class);

    VehicleAffectationDto toDto(VehicleAffectation vehicleAffectation);

    @InheritInverseConfiguration
    VehicleAffectation toEntity(VehicleAffectationDto vehicleDto);

    List<VehicleAffectationDto> toDto(List<VehicleAffectation> vehiclesAffectation);
    List<VehicleAffectation> toEntity(List<VehicleAffectationDto> vehicleDtosAffectation);


}
