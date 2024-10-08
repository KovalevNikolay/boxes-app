package ru.kovalev.boxesloader.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kovalev.boxesapp.mapper.BoxesMapper;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class BoxesMapperTest {

    @Mock
    private BoxesRepository boxesRepository;

    @InjectMocks
    private BoxesMapper boxesMapper;

    BoxesMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenStringIsEmptyThenEmptyList() {
        String input = "";
        List<Box> boxes = boxesMapper.mapToList(input);
        assertThat(boxes).isEmpty();
    }

    @Test
    void whenStringContainsBoxNamesThenListSizeEqualCountNamesInString() {
        when(boxesRepository.findByName("штанга"))
                .thenReturn(Optional.of(new Box("штанга", null, "")));
        when(boxesRepository.findByName("комп"))
                .thenReturn(Optional.of(new Box("комп", null, "")));

        String input = "штанга,комп";
        List<Box> boxes = boxesMapper.mapToList(input);
        assertThat(boxes).hasSize(2);
    }
}
