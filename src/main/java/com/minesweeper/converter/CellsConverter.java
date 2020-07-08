package com.minesweeper.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minesweeper.model.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class CellsConverter implements AttributeConverter<Cell[][], String> {
    private Logger logger = LoggerFactory.getLogger(CellsConverter.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Cell[][] cells) {

        String json = null;
        try {
            json = objectMapper.writeValueAsString(cells);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return json;
    }

    @Override
    public Cell[][] convertToEntityAttribute(String customerInfoJSON) {

        Cell[][] cells = null;
        try {
            cells = objectMapper.readValue(customerInfoJSON, Cell[][].class);
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return cells;
    }

}