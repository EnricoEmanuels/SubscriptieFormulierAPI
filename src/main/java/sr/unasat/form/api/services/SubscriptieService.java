package sr.unasat.form.api.services;

import sr.unasat.form.api.dto.SubscriptieDTO;
import sr.unasat.form.api.entities.Subscriptie;
import sr.unasat.form.api.repositories.SubscriptieRepository;

import java.util.List;
import java.util.ArrayList;

public class SubscriptieService {

    private SubscriptieRepository subscriptieRepository;

    public SubscriptieService(SubscriptieRepository subscriptieRepository) {
        this.subscriptieRepository = subscriptieRepository;
    }

    public Subscriptie saveSubscriptie(Subscriptie subscriptie) {
        subscriptieRepository.save(subscriptie);
        return subscriptie;
    }

    public List<Subscriptie> getAllsubscrripties() {
        return subscriptieRepository.getAll();
    }

    public Subscriptie findSubscriptieById(int id) {
        return subscriptieRepository.findById(id);
    }

    public void deleteSubscriptie(int id) {
        subscriptieRepository.deleteById(id);
    }

    public void updateSubscriptie(Subscriptie subscriptie) {
        subscriptieRepository.update(subscriptie);
    }



}
