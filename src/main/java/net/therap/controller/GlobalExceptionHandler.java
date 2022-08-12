package net.therap.controller;

import net.therap.exception.InsufficientAccessException;
import net.therap.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author khandaker.maruf
 * @since 7/28/22
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static String ERROR_VIEW = "errorPage";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public ModelAndView handleRecordNotFoundException(RecordNotFoundException exception) {
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        setUpReferenceData(mav, HttpStatus.NOT_FOUND, exception.getMessage());

        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(NumberFormatException exception) {
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        setUpReferenceData(mav, HttpStatus.BAD_REQUEST, exception.getMessage());

        return mav;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InsufficientAccessException.class)
    public ModelAndView handleInsufficientAccessException(InsufficientAccessException exception) {
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        setUpReferenceData(mav, HttpStatus.UNAUTHORIZED, exception.getMessage());

        return mav;
    }

    private void setUpReferenceData(ModelAndView mav, HttpStatus errorCode, String message) {
        mav.addObject("httpErrorCode", errorCode.value());
        mav.addObject("errorMessage", message);
    }
}
