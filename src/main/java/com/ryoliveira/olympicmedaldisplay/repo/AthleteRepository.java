package com.ryoliveira.olympicmedaldisplay.repo;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    List<Athlete> findAllByCountry(String country);

    Optional<Athlete> findByName(String name);

    List<Athlete> findAllByDiscipline(String sport);
}
