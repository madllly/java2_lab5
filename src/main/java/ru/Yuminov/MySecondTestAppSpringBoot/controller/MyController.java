package ru.yuminov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yuminov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.yuminov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.yuminov.MySecondTestAppSpringBoot.model.*;
import ru.yuminov.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.yuminov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.yuminov.MySecondTestAppSpringBoot.service.ModifySourceRequestService;
import ru.yuminov.MySecondTestAppSpringBoot.service.ValidationService;
import ru.yuminov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.time.Instant;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;
    private final ModifySourceRequestService modifySourceRequestService;

    @Autowired
    public MyController(
            ValidationService validationService,
            @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
            @Qualifier("ModifySourceRequestService") ModifyRequestService modifyRequestService,
            @Qualifier("ModifySourceRequestService") ModifySourceRequestService modifySourceRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.modifySourceRequestService = modifySourceRequestService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {
        log.info("Received request: {}", request);
        request.setReceivedTime(Instant.now());

        Response response = buildInitialResponse(request);

        try {
            validateRequest(bindingResult, request);
        } catch (ValidationFailedException e) {
            log.error("ValidationFailedException occurred: {}", e.getMessage());
            return buildErrorResponse(response, Codes.FAILED, ErrorCodes.VALIDATION_EXCEPTION, ErrorMessages.VALIDATION, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception occurred: {}", e.getMessage());
            return buildErrorResponse(response, Codes.FAILED, ErrorCodes.UNKNOWN_EXCEPTION, ErrorMessages.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        applyModifications(request, response);
        log.info("Final response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Response buildInitialResponse(Request request) {
        return Response.builder()
                .id(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

    private void validateRequest(BindingResult bindingResult, Request request) {
        if (bindingResult.hasErrors()) {
            log.error("BindingResult has errors: {}", bindingResult.getAllErrors());
            throw new ValidationFailedException("Validation failed", "ValidationFailedException");
        }
        validationService.isValid(bindingResult);

        if ("123".equals(request.getUid())) {
            log.error("Unsupported UID: {}", request.getUid());
            throw new UnsupportedCodeException("UID не может быть равен 123", "UnsupportedCodeException");
        }
    }

    private void applyModifications(Request request, Response response) {
        modifySourceRequestService.modify(request);
        modifyRequestService.modify(request);
        modifyResponseService.modify(response);
    }

    private ResponseEntity<Response> buildErrorResponse(Response response, Codes code, ErrorCodes errorCode, ErrorMessages errorMessage, HttpStatus status) {
        response.setCode(code);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        log.info("Updated response: {}", response);
        return new ResponseEntity<>(response, status);
    }
}
