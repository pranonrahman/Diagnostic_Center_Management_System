package net.therap.dms.controller;

import net.therap.dms.exception.InsufficientAccessException;
import net.therap.dms.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
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

    @Autowired
    private MessageSourceAccessor msa;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public ModelAndView handleRecordNotFoundException(RecordNotFoundException exception) {
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        setUpReferenceData(mav, HttpStatus.NOT_FOUND, msa.getMessage("error.notFound"));

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
        setUpReferenceData(mav, HttpStatus.UNAUTHORIZED, msa.getMessage("error.unAuthorized"));

        return mav;
    }

    private void setUpReferenceData(ModelAndView mav, HttpStatus errorCode, String message) {
        mav.addObject("httpErrorCode", errorCode.value());
        mav.addObject("errorMessage", message);
    }
}
