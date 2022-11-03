package com.ecjtu.onlinemusic.handler;


import com.ecjtu.onlinemusic.entity.vo.ResultEnum;
import com.ecjtu.onlinemusic.entity.vo.ResultVO;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


//全局异常处理,并返回给前端！
@RestControllerAdvice    //由@ControllerAdvice、@ResponseBody组成。  而@ControllerAdvice本质是@Component
public class GlobalExceptionHandler {

    //处理control层参数校验异常
    @ExceptionHandler(value = BindException.class)
    public ResultVO handleValidationException(BindException be){

        List<String> data=new ArrayList<>();
        BindingResult bindingResult = be.getBindingResult();
        //获取到校验错误的字段信息集合
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //循环获取错误提示信息
        for(FieldError error:fieldErrors){
            data.add(error.getDefaultMessage());
        }
        //  把异常参数返回给前端
        return new ResultVO(ResultEnum.VALIDATE_FAILED,data);
    }

    //处理json异常信息
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultVO handleAllException(HttpMessageNotReadableException e){
        e.printStackTrace();
        return  new ResultVO(ResultEnum.JSON_ERROR,e.getMessage());
    }

    // 数据库异常
    @ExceptionHandler(SQLException.class)
    public ResultVO sqlException(SQLException e) {
        return new ResultVO("数据库异常，操作失败!");
    }

    //处理所有的异常信息
    @ExceptionHandler(value = Exception.class)
    public ResultVO handleAllException(Exception e){
        e.printStackTrace();
        return  new ResultVO(ResultEnum.ERROR,e.getMessage());
    }

        //全局异常处理 处理参数校验异常
//    @ExceptionHandler(value = ConstraintViolationException.class)
//    public ResultVO handleValidationException(ConstraintViolationException cve){
//        List<String> data=new ArrayList<>();
//        //获取校验错误信息集合
//        for(ConstraintViolation violation:cve.getConstraintViolations()){
//            data.add(violation.getMessage());
//        }
//        return new ResultVO(ResultEnum.VALIDATE_FAILED,data);
//    }
}
