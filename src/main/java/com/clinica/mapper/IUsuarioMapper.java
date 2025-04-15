package com.clinica.mapper;

import com.clinica.dto.UsuarioDto;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import org.mapstruct.Mapping;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {IRolMapper.class})
public interface IUsuarioMapper {

    @Mapping(source = "activo", target = "activo")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToStrings")
    UsuarioDto toDto(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "ultimaModificacion", ignore = true)
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapStringsToRoles")
    Usuario toEntity(UsuarioDto usuarioDto);

    // Actualización para manejar nulls más seguro
    @Named("mapRolesToStrings")
    default Set<String> mapRolesToStrings(Set<Rol> roles) {
        return roles == null ? null : roles.stream()
                .map(Rol::getNombre)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Named("mapStringsToRoles")
    default Set<Rol> mapStringsToRoles(Set<String> roleNames) {
        return roleNames == null ? null : roleNames.stream()
                .filter(Objects::nonNull)
                .map(name -> Rol.builder().nombre(name).build())
                .collect(Collectors.toSet());
    }
}



