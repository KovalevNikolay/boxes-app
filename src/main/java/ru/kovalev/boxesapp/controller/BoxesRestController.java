package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.dto.Truck;
import ru.kovalev.boxesapp.entity.Box;
import ru.kovalev.boxesapp.service.BoxesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
public class BoxesRestController {

    private final BoxesService boxesService;
    private final LoadController loadController;

    @GetMapping
    public ResponseEntity<List<BoxDto>> getAllBoxes() {
        List<BoxDto> boxes = boxesService.getAll();
        return ResponseEntity.ok(boxes);
    }

    @GetMapping("/{name}")
    public ResponseEntity<BoxDto> getBox(@PathVariable String name) {
        Optional<BoxDto> box = boxesService.getByName(name);
        return box.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteBox(@PathVariable String name) {
        if (boxesService.delete(name)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Box> addBox(@RequestBody BoxDto boxDto) {
        Box createdBox = boxesService.add(boxDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBox);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Void> updateBox(@PathVariable String name, @RequestBody BoxDto boxDto) {
        boxDto.setName(name);
        if (boxesService.update(boxDto)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/load-boxes")
    public ResponseEntity<List<Truck>> loadBoxes(
            @RequestParam String boxes,
            @RequestParam String trucks,
            @RequestParam String strategy
    ) {
        List<Truck> loadedResult = loadController.loadBoxes(boxes, trucks, strategy);
        return ResponseEntity.ok(loadedResult);
    }

//    @GetMapping("/load-boxes-from-json")
//    public ResponseEntity<List<Truck>> loadBoxes(
//            @RequestBody List<BoxDto> boxes,
//            @RequestParam String trucks,
//            @RequestParam String strategy
//    ) {
//        List<Truck> loadedResult = loadController.loadBoxesFromFile(boxes, trucks, strategy);
//        return ResponseEntity.ok(loadedResult);
//    }

//    @GetMapping("/truck-analyze")
//    public ResponseEntity<List<AnalyzeResult>>
}
