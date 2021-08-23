package com.ryoliveira.olympicmedaldisplay.repo;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

public interface AthleteMongoRepository extends MongoRepository<Athlete, String> {

    Page<Athlete> findAllByCountry(String country, Pageable pageable);

    Optional<Athlete> findByName(String name);

    Page<Athlete> findAllByDiscipline(String sport, Pageable pageable);

    Page<Athlete> findAllByDisciplineAndCountry(String sport, String country, Pageable pageable);

}
