package com.example.examen.domain.validation;

import com.example.examen.domain.City;
import com.example.examen.domain.TrainStation;

/**
 * User validator where is verified the inputs of a potential user
 */
public class TrainStationValidator implements Validator<TrainStation> {
    @Override
    public void validate(TrainStation entity) throws ValidationException {
        Integer id = entity.getTrainId();
        Integer idDeparture = entity.getDepartureCityId();
        Integer idDestination = entity.getDepartureCityId();
        if(id<1)
            throw new ValidationException("Train id must be positive");
        if(idDeparture<1)
            throw new ValidationException("Departure city id must be positive");
        if(idDestination<1)
            throw new ValidationException("Destination city id must be positive");

    }
}
