package pl.scoutbook.exception.handlers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import pl.scoutbook.config.ResponseRegisterValidation;
import pl.scoutbook.model.Errors;

//@EnableWebMvc
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	public ResponseRegisterValidation responseRegisterValidation;
	
    @ExceptionHandler({ RepositoryConstraintViolationException.class })
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
  	  Set<String> errors = ex.getBindingResult()
  			.getAllErrors()
  			.stream()
  			.map(p -> p.toString())
  			.collect(Collectors.toSet());
  	  
  	  Errors responseErrors = responseRegisterValidation.makeRegisterResponse(errors);
  	 
        return new ResponseEntity<Object>(responseErrors, headers, HttpStatus.NOT_ACCEPTABLE);
    }
    
}