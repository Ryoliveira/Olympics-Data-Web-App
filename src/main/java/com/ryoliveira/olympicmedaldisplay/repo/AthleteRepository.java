package com.ryoliveira.olympicmedaldisplay.repo;

import com.ryoliveira.olympicmedaldisplay.model.*;
import org.springframework.data.jpa.repository.*;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

}
