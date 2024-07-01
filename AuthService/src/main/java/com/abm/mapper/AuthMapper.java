package com.abm.mapper;



import com.abm.dto.request.AuthRegisterDto;
import com.abm.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    Auth authRegisterDtoToAuth(AuthRegisterDto authRegisterDTO);




}
