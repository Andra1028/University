package com.example.examen.domain.validation;

import com.example.examen.domain.City;

/**
 * User validator where is verified the inputs of a potential user
 */
public class CityValidator implements Validator<City> {
    @Override
    public void validate(City entity) throws ValidationException {
        String name=  entity.getCityName();
        Integer id = entity.getCityId();
         if(!name.matches("^[A-Z].*"))
            throw new ValidationException(" the name must start with a big letter");
        if(id<1)
            throw new ValidationException("id must be positive");
        }
}
