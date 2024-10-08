package ru.kovalev.boxesloader.repository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kovalev.boxesapp.io.BoxesInputOutput;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BoxesRepositoryTest {
    @Mock
    private BoxesInputOutput boxesInputOutput;
    @InjectMocks
    private BoxesRepository boxesRepository;
    private final Box box1, box2;

    BoxesRepositoryTest() {
        MockitoAnnotations.openMocks(this);

        box1 = new Box("Box1", null, "@");
        box2 = new Box("Box2", null, "#");

        boxesRepository.init();
        boxesRepository.save(box1);
        boxesRepository.save(box2);
    }

    @Test
    void testFindAll() {
        List<Box> boxes = boxesRepository.findAll();
        assertThat(boxes)
                .hasSize(2)
                .containsExactlyInAnyOrder(box1, box2);
    }

    @Test
    void testFindByName_Success() {
        Optional<Box> foundBox = boxesRepository.findByName("Box1");
        assertThat(foundBox).contains(box1);
    }

    @Test
    void testFindByName_NotFound() {
        Optional<Box> foundBox = boxesRepository.findByName("NonExistentBox");
        assertThat(foundBox).isEmpty();
    }

    @Test
    void testFindByMarker_Success() {
        Optional<Box> foundBox = boxesRepository.findByMarker("@");
        assertThat(foundBox).contains(box1);
    }

    @Test
    void testFindByMarker_NotFound() {
        Optional<Box> foundBox = boxesRepository.findByMarker("NonExistentMarker");
        assertThat(foundBox).isEmpty();
    }

    @Test
    void testDeleteByName_Success() {
        boolean result = boxesRepository.deleteByName("Box1");
        assertThat(result).isTrue();
        Optional<Box> foundBox = boxesRepository.findByName("Box1");
        assertThat(foundBox).isEmpty();
    }

    @Test
    void testDeleteByName_NotFound() {
        boolean result = boxesRepository.deleteByName("NonExistentBox");
        assertThat(result).isFalse();
    }

    @Test
    void testUpdate_Success() {
        Box updatedBox = new Box("Box1", null, "NewMarker");
        boolean result = boxesRepository.update(updatedBox);
        assertThat(result).isTrue();

        Optional<Box> foundBox = boxesRepository.findByName("Box1");
        assertThat(foundBox)
                .isPresent()
                .get()
                .extracting(Box::getMarker)
                .isEqualTo("NewMarker");
    }

    @Test
    void testUpdate_NotFound() {
        Box nonExistentBox = new Box("NonExistentBox", null, "Marker");
        boolean result = boxesRepository.update(nonExistentBox);
        assertThat(result).isFalse();
    }

    @Test
    void testSave_Success() {
        Box newBox = new Box("Box3", null, "Marker3");
        boolean result = boxesRepository.save(newBox);
        assertThat(result).isTrue();

        Optional<Box> foundBox = boxesRepository.findByName("Box3");
        assertThat(foundBox).contains(newBox);
    }

    @Test
    void testSave_AlreadyExists() {
        Box existingBox = new Box("Box1", null, "Marker1");
        boolean result = boxesRepository.save(existingBox);
        assertThat(result).isFalse();
    }
}
