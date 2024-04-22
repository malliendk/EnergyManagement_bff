package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
@AllArgsConstructor
public class DtoController {

    private final DtoService dtoService;

    @PostMapping()
    public ResponseEntity<GameDto> initGameDto(@RequestBody GameDto initializedGameDto) {
        final GameDto initGameDto = dtoService.getGameDtoFromController(initializedGameDto);
        return ResponseEntity.ok(initGameDto);
    }


    @PutMapping("start")
    public void startScheduledevents(@RequestBody GameDto gameDto) {
        dtoService.startEventScheduler(gameDto);
    }
}
