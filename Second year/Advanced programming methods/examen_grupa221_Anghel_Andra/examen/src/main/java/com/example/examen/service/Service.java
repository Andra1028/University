package com.example.examen.service;

import com.example.examen.domain.City;
import com.example.examen.domain.TrainStation;
import com.example.examen.repository.Repo;
import com.example.examen.utils.ObservableImplementat;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Service extends ObservableImplementat {

    private Repo<City> cityRepo;

    private Repo<TrainStation> trainRepo;

    public Service(Repo<City> cityRepo,Repo<TrainStation> trainRepo) {
        this.cityRepo = cityRepo;
        this.trainRepo=trainRepo;
    }

    public Iterable<City> getCities()
    {
        return cityRepo.findAll();
    }
    public Iterable<TrainStation> getTrainStations(){return trainRepo.findAll();}
    public Iterable<TrainStation> findDirectTrainByCities(Integer departure, Integer destination)
    {
        Set<TrainStation> ts= new HashSet<>();
        int ok=0;
        Set<TrainStation> rez= new HashSet<>();
        for(TrainStation t: trainRepo.findAll())
        {
            if(t.getDepartureCityId()==departure)
            {
                for(TrainStation t2: trainRepo.findAll())
                {
                    if(Objects.equals(t2.getTrainId(), t.getTrainId()))
                    {
                        ts.add(t2);
                        if(t2.getDestinationCityId()==destination)
                        {
                            ok=1;
                            rez=ts;
                            ///System.out.println(rez);
                            return ts;
                            //break;
                        }
                    }
                }
                ts.clear();
            }
        }

        //System.out.println(rez);
       // if(ok==1)
           // return rez;
        return null;
    }

    public City findCityById(Integer id){
        for(City c : cityRepo.findAll())
            if(Objects.equals(c.getCityId(), id))
                return c;
        return null;
    }

    public City findCityByName(String name){
        for(City c : cityRepo.findAll())
            if(Objects.equals(c.getCityName(), name))
                return c;
        return null;
    }

}
