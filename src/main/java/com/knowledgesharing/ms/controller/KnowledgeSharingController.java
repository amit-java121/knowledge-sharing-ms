package com.knowledgesharing.ms.controller;

import com.knowledgesharing.ms.datatransfer.KnowledgeSharingDto;
import com.knowledgesharing.ms.datatransfer.KnowledgeSharingModelView;
import com.knowledgesharing.ms.exception.ErrorResponse;
import com.knowledgesharing.ms.service.KnowledgeSharingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.knowledgesharing.ms.constant.Constant.ROLE_ADMIN;
import static com.knowledgesharing.ms.constant.Constant.ROLE_CUSTOMER;

@RestController
@RequestMapping("/v1/knowledge-sharing")
@AllArgsConstructor
@Tag(name = "Knowledge-Sharing-Controller", description = "(Version 1) Controller that basically does the CRUD operations related to the Knowledge sharing platform!")
public class KnowledgeSharingController {

    private final KnowledgeSharingService knowledgeSharingService;

    @Operation(description = "Get details related to the knowledge sharing", operationId = "fetchDetails")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = KnowledgeSharingDto.class)))), @ApiResponse(responseCode = "400", description = "Bad Request (when the values of the input not in the correct way)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(value = "", produces = {"application/json", "application/xml"})
    @PreAuthorize(ROLE_CUSTOMER + " OR " + ROLE_ADMIN)
    public KnowledgeSharingModelView fetchDetails(@RequestParam(required = false) String author, @RequestParam(required = false) String title, @RequestParam(required = false) Long likes, @RequestParam(required = false) Long views) {
        return knowledgeSharingService.fetchDetails(author, title, likes, views);
    }

    @Operation(description = "Insert new details into the knowledge sharing DB", operationId = "insertDetails")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @PostMapping(value = "", produces = {"application/json", "application/xml"})
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<Long> insertDetails(@Valid @RequestBody KnowledgeSharingDto knowledgeSharingDto) {
        Long id = knowledgeSharingService.insertDetails(knowledgeSharingDto);
        return ResponseEntity.created(URI.create("/knowledge-sharing/" + id)).body(id);


    }

    @Operation(description = "Modify existing details from the knowledge sharing DB", operationId = "modifyDetails")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"), @ApiResponse(responseCode = "404", description = "ID not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    @PreAuthorize(ROLE_CUSTOMER)
    public ResponseEntity<Long> modifyDetails(@PathVariable("id") Long id, @RequestBody KnowledgeSharingDto knowledgeSharingDto) {
        Long responseId = knowledgeSharingService.modifyDetails(id, knowledgeSharingDto);
        return ResponseEntity.ok(responseId);
    }

    @Operation(description = "Delete existing details from the knowledge sharing DB", operationId = "deleteDetails")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @DeleteMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<Void> deleteDetails(@PathVariable("id") Long id, Authentication authentication) {
        knowledgeSharingService.deleteDetails(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
