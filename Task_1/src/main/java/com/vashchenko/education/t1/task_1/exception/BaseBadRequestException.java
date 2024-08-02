package com.vashchenko.education.t1.task_1.exception;

import com.vashchenko.education.t1.task_1.model.dto.response.ExtraErrorMessage;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;



public class BaseBadRequestException extends BaseException{
    private final List<ExtraErrorMessage> errors = new LinkedList<>();
    public BaseBadRequestException(String key, String type,String error) {
        errors.add(new ExtraErrorMessage(error,key,type));
    }

    public void addError(String key, String type,String error){
        errors.add(new ExtraErrorMessage(error,key,type));
    }
    public void addErrors(Collection<ExtraErrorMessage> errorsToAdd){
        errors.addAll(errorsToAdd);
    }
    public List<ExtraErrorMessage> getErrors(){
        return new LinkedList<>(errors);
    }
}
