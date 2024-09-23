package com.example.mp.mapper;

import com.example.mp.dto.UserStockDto;
import com.example.mp.entity.UserStockEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStockMapper {
    UserStockDto toDto(UserStockEntity userStockEntity);
    UserStockEntity toEntity(UserStockDto userStockDto);
}
