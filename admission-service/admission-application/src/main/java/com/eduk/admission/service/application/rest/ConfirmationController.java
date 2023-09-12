package com.eduk.admission.service.application.rest;

import com.eduk.domain.dto.create.CreateConfirmationCommand;
import com.eduk.domain.dto.create.CreateConfirmationResponse;
import com.eduk.domain.dto.track.TrackConfirmationQuery;
import com.eduk.domain.dto.track.TrackConfirmationResponse;
import com.eduk.domain.ports.input.service.ConfirmationApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value="api/v1/confirmation",produces = "application/vnd.api.v1")
public class ConfirmationController {
    private final ConfirmationApplicationService confirmationApplicationService;


    public ConfirmationController(ConfirmationApplicationService confirmationApplicationService)
    {
        this.confirmationApplicationService = confirmationApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateConfirmationResponse> createConfirmation(
            @RequestBody CreateConfirmationCommand createConfirmationCommand)
    {
        log.info("Creating confirmation for application id :{} ",createConfirmationCommand.getApplicationId());
        CreateConfirmationResponse createConfirmationResponse = confirmationApplicationService.createConfirmation(createConfirmationCommand);
        log.info("Confirmation created with tracking id :{} ",createConfirmationResponse.getConfirmationTrackingId());

        return  ResponseEntity.ok(createConfirmationResponse);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackConfirmationResponse> getConfirmationByTrackingId(
            @PathVariable UUID trackingId
    ){
        TrackConfirmationResponse trackConfirmationResponse = confirmationApplicationService.trackConfirmation(
                TrackConfirmationQuery.builder().confirmationTrackingId(trackingId).build()
        );
        log.info("Returning confirmation status with tracking id :{} ",trackConfirmationResponse.getConfirmationTrackingId());
        return ResponseEntity.ok(trackConfirmationResponse);
    }
}
