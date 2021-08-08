package com.ryoliveira.olympicmedaldisplay.service;

import com.ryoliveira.olympicmedaldisplay.model.*;

public interface SportService {

    SportsList getSportList();
    SportInformation getSportInformation(String sport);

}
