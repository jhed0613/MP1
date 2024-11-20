package com.example.mp.mapper;

import com.example.mp.dto.BoardDto;
import com.example.mp.entity.BoardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    BoardDto toDto(BoardEntity boardEntity);
    BoardEntity toEntity(BoardDto boardDto);
}

