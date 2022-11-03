package io.github.rogerfilipes.application.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MapConfig {
}
