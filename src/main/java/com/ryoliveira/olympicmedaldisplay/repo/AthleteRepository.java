package com.ryoliveira.olympicmedaldisplay.repo;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    Page<Athlete> findAllByCountry(String country, Pageable pageable);

    Optional<Athlete> findByName(String name);

    Page<Athlete> findAllByDiscipline(String sport, Pageable pageable);

    Page<Athlete> findAllByDisciplineAndCountry(String sport, String country, Pageable pageable);
}
