package com.clinica.mapper;

import com.clinica.dto.UsuarioDto;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToStrings")
    UsuarioDto toDto(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapStringsToRoles")
    Usuario toEntity(UsuarioDto usuarioDto);

    @Named("mapRolesToStrings")
    default Set<String> mapRolesToStrings(Set<Rol> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());
    }

    @Named("mapStringsToRoles")
    default Set<Rol> mapStringsToRoles(Set<String> roleNames) {
        if (roleNames == null) {
            return null;
        }
        return roleNames.stream()
                .map(name -> Rol.builder().nombre(name).build())
                .collect(Collectors.toSet());
    }
}



