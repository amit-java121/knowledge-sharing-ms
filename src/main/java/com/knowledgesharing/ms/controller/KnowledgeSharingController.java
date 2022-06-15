package com.knowledgesharing.ms.controller;

import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import com.knowledgesharing.ms.datatransfer.KnowledgeSharingModelView;
import com.knowledgesharing.ms.service.KnowledgeSharingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/knowledge-sharing")
@AllArgsConstructor
@Tag(name = "Knowledge-Sharing-Controller", description = "Controller that basically does the CRUD operations related to the Knowledge sharing platform!")
public class KnowledgeSharingController {

    private final KnowledgeSharingService knowledgeSharingService;

    @Operation(description = "Get details related to the knowledge sharing", operationId = "fetchDetails")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "")
    public KnowledgeSharingModelView fetchDetails(@RequestParam(required = false) String author, @RequestParam(required = false) String title, @RequestParam(required = false) Long likes, @RequestParam(required = false) Long views) {
        return knowledgeSharingService.fetchDetails(author, title, likes, views);
    }

    @Operation(description = "Insert new details into the knowledge sharing DB", operationId = "insertDetails")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping(value = "")
    public ResponseEntity<Long> insertDetails(@Valid @RequestBody KnowledgeSharingDto knowledgeSharingDto) {
        Long id = knowledgeSharingService.insertDetails(knowledgeSharingDto);
        return ResponseEntity.created(URI.create("/knowledge-sharing/" + id)).body(id);


    }

    @Operation(description = "Modify existing details from the knowledge sharing DB", operationId = "modifyDetails")
    @ApiResponse(responseCode = "200", description = "OK")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Long> modifyDetails(@PathVariable("id") Long id, @RequestBody KnowledgeSharingDto knowledgeSharingDto) {
        Long responseId = knowledgeSharingService.modifyDetails(id, knowledgeSharingDto);
        return ResponseEntity.ok(responseId);
    }

    @Operation(description = "Delete existing details from the knowledge sharing DB", operationId = "deleteDetails")
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteDetails(@PathVariable("id") Long id) {
        knowledgeSharingService.deleteDetails(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
