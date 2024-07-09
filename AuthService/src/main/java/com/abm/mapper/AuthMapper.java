package com.abm.mapper;



import com.abm.entity.Auth;
import com.abm.request.AuthRegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    Auth authRegisterDtoToAuth(AuthRegisterDto authRegisterDTO);




}
