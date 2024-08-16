package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(final ValidationException e) {
        return new ErrorResponse("Ошибка валидации: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        return new ErrorResponse("Ресурс не найден: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(final ConflictException e) {
        return new ErrorResponse("Конфликт в программе: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(final ForbiddenException e) {
        return new ErrorResponse("Ошибка доступа: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(final BadRequestException e) {
        return new ErrorResponse("Неправильный запрос: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new ErrorResponse("500 ERROR: ", sw.toString());
    }
}
