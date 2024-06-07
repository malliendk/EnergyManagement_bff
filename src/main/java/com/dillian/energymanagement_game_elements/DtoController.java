package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.service.InitiateDtoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@AllArgsConstructor
public class DtoController {

    private final DtoService dtoService;
    private final InitiateDtoService initiateDtoService;

    @PostMapping()
    public ResponseEntity<GameDto> initGameDto(@RequestBody InitiateGameDto initDto) {
        initiateDtoService.storeInitiateGameDto(initDto);
        GameDto startingGameDto = dtoService.getStartingDto();
//        dtoService.startSchedulers(startingGameDto);
        return ResponseEntity.ok(startingGameDto);
    }

//    @GetMapping
//    public ResponseEntity<GameDto> getUpdatedDto(@RequestBody GameDto gameDto) {
//        GameDto updatedDto = dtoService.getUpdatedDto(gameDto);
//        return ResponseEntity.ok(updatedDto);
//    }



//    @PutMapping("start")
//    public void startScheduledevents(@RequestBody GameDto gameDto) {
//        dtoService.startSchedulers(gameDto);
//    }
}
