package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;

public interface DataScrapeService {
    TeamList getStandings(String sport);
    SportsList getSportsList();
}
