package sn.sastrans.backofficev2.carburant.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sn.sastrans.backofficev2.carburant.dto.CuveDto;
import sn.sastrans.backofficev2.carburant.models.Cuve;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CuveMapper extends EntityMapper<CuveDto, Cuve> {

    CuveMapper INSTANCE = Mappers.getMapper(CuveMapper.class);


    CuveDto toDto(Cuve cuve);

    @InheritInverseConfiguration
    Cuve toEntity(CuveDto cuveDto);

    List<CuveDto> toDto(List<Cuve> cuves);
    List<Cuve> toEntity(List<CuveDto> cuveDtos);

}
