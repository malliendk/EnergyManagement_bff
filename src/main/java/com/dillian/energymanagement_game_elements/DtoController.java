package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@AllArgsConstructor
public class DtoController {

    private final DtoService dtoService;

    @PostMapping()
    public ResponseEntity<GameDto> initGameDto(@RequestBody InitiateGameDto initDto) {
        initDto = dtoService.initiateGameDto(initDto);
        GameDto startingGameDto = dtoService.
        dtoService.startSchedulers(startingGameDto);
        return ResponseEntity.ok(startingGameDto);
    }

    @GetMapping
    public ResponseEntity<GameDto> getUpdatedDto(@RequestBody GameDto gameDto) {
        GameDto updatedDto = dtoService.getUpdatedDto(gameDto);
        return ResponseEntity.ok(updatedDto);
    }



    @PutMapping("start")
    public void startScheduledevents(@RequestBody GameDto gameDto) {
        dtoService.startSchedulers(gameDto);
    }
}
