package com.ryoliveira.olympicmedaldisplay.controller;

import com.ryoliveira.olympicmedaldisplay.model.*;
import com.ryoliveira.olympicmedaldisplay.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataController {

    @Autowired
    private DataScrape dataScrape;

    @GetMapping("/standings")
    public TeamList getStandings(){
        return this.dataScrape.getStandings();
    }

    @GetMapping("/sports")
    public SportsList getSportsList(){
        return this.dataScrape.getSportsList();
    }


}
