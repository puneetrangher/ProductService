package com.scaler.productservice.controlleradvices;

import com.scaler.productservice.dtos.ExceptionDto;
import com.scaler.productservice.exceptions.ProductDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlers {

    /*
    * This method will handle the ProductDoesNotExistException
    * and return a ResponseEntity with the ExceptionDto and HttpStatus.NOT_FOUND
    * It takes the ProductDoesNotExistException as an argument and uses the message from it
     */
    @ExceptionHandler(value = {ProductDoesNotExistException.class})
    public ResponseEntity<ExceptionDto> handleProductDoesNotExistException(ProductDoesNotExistException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
//
//    @ExceptionHandler(value = {categoryDoesNotExistException.class})
//    public void handleCategoryDoesNotExistException() {
//
//    }
//
//    @ExceptionHandler(value = {productAlreadyExistsException.class})
//    public void handleProductAlreadyExistsException() {
//
//    }
//
//    @ExceptionHandler(value = {categoryAlreadyExistsException.class})
//    public void handleCategoryAlreadyExistsException() {
//
//    }
//
//    @ExceptionHandler(value = {productNotInCategoryException.class})
//    public void handleProductNotInCategoryException() {
//
//    }

    @ExceptionHandler(value = {ArithmeticException.class})
    public ResponseEntity<Void> handleArithmeticException() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
