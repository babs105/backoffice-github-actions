package sn.sastrans.backofficev2.carburant.services;


import sn.sastrans.backofficev2.carburant.models.Rajout;

import java.util.List;

public interface RajoutService {
    Rajout saveRajout(Rajout rajout);
    List<Rajout> getAllRajout();
    Rajout getRajoutById(int id);
    void deleteRajout(int id);
    
}
