package sr.unasat.form.api.repositories;

import sr.unasat.form.api.entities.Subscriptie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import sr.unasat.form.api.config.JPAConfig;
import sr.unasat.form.api.interfac.EntityRepository;

import java.util.List;

public class SubscriptieRepository implements EntityRepository<Subscriptie> {

    //    EntityManager entityManager = JPAConfig.getEntityManger(); en ik heb een object gemaaakt in die main classe


    private EntityManager entityManager;

    public SubscriptieRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void save(Subscriptie subscriptie) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(subscriptie);
        transaction.commit();

    }

    @Override
    public void update(Subscriptie subscriptie) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Subscriptie updateSubscription = entityManager.find(Subscriptie.class, subscriptie.getId());
        if (updateSubscription != null) {
            updateSubscription.setFirstname(subscriptie.getFirstname());
            updateSubscription.setLastname(subscriptie.getLastname());
            updateSubscription.setEmail(subscriptie.getEmail());
            updateSubscription.setPhonenumber(subscriptie.getPhonenumber());
            entityManager.merge(updateSubscription);  // Zorg ervoor dat de update wordt toegepast
        }
        transaction.commit();
    }

    @Override
    public List<Subscriptie> getAll() {
        return entityManager.createQuery("SELECT s FROM Subscriptie s", Subscriptie.class).getResultList();
    }

    public void deleteById(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Subscriptie subscription = entityManager.find(Subscriptie.class, id);
        if (subscription != null) {
            entityManager.remove(subscription);
        }
        transaction.commit();
    }

    public Subscriptie findById(int id) { // verwacht alleen een id wat als je die Subscription subscription
        return entityManager.find(Subscriptie.class, id);
    }

}

// waar je return zet daar gaat je methode eindigen dan hoef je niet perse een else block te maken

// defensive coding betekent dat je een check doet en als het niet voldoet aan die check dan ben je klaar ermee

// defensive coding je gaat direk stoppen als dat ding fout gaat en je stopt met een return

// om een else blok te vermijden dan pas je defensive coding toe om je code simpeler te maken

// defensive coding is een goeie praktijk

/*

public int delen(int a, int b) {
    if (a == b) {
        System.out.println("0 / 0 = 0");
    }
    return a / b;
}

 */




/* een repository gebruiken of DAO ( data acces object) voor http verzoeken
 opslaan in die database, omdat ze spraken over validatie en een student zei dat ze dachten om
   die validatie te plaatsen in die dao of die service layer or die crud handeling ze gingen
   redenenren dus wat is beter een dao of repository voor http is het repository want dan kan je
   vslidatie zetten in je repository of je service layer ?? maar mijn meester zei in een dao
   behoort er geen validation te zijn een dao is gewoon om data te accessen niks anders en
   die repository dan is mijn vraag het is hetzelfde als een repositry het enigste verschill is dat
   een repository is geconcentreerd op een tabel en een dao is geconcentreerd op of een dao heeft als uitgang punt dat
   je data queried en terug krijgt op de manier hoe je het zou willen dus dan maak je meestal in plaats an
   een entity een model want in je model kan je zeggen wat je wilt . (mijn eerste vraag wat is een model)
   laten we zeggen je queried op 3 tabellen met joins en er zijn specifieke velden die je wilt en hij retourneert
   informatie uit die drie tabellen dan kan je een dao gebruiken over het algememeen werk je ook met repositories

   */

/* mijn meester zei ik moet die informatie netjes vertonen in plaats
van jason te gebruiken in plaats van jason records dat ik gewoon records toon en geen jason eronder
wat bedoelt hij daarmee records */
