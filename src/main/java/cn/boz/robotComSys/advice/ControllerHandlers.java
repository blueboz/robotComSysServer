package cn.boz.robotComSys.advice;

import cn.boz.robotComSys.pojo.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice("cn.boz.robotComSys")
public class ControllerHandlers {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult errorHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String message=fieldErrors.stream().map(field->{
            return field.getField()+":"+field.getDefaultMessage();
        }).collect(Collectors.joining(","));
        return new ResponseResult(400,message);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseResult errorHandlerException(Exception e){
        e.printStackTrace();
        return new ResponseResult(400,"服务器挂了"+e.getMessage());
    }

}
