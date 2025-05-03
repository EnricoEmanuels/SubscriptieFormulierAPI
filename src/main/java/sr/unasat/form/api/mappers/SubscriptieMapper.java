package sr.unasat.form.api.mappers;

import sr.unasat.form.api.dto.SubscriptieDTO;
import sr.unasat.form.api.entities.Subscriptie;

// je hebt twee methodes 1 methode die een subscriptie pakt en de tweede die die de velden overzet naar een dto
// en een ander methode die de DTo pakt en zet tot en entity
// als je een paar velden weghaalt gaat het geen probleem zijn het werkt gewoon met de velden die je aangeeft
// dus jij bepaalt hoe je mappers werken
// mapper zonder static in een werkelijk applicatie ga je veel mappers hebben

public class SubscriptieMapper {
    public SubscriptieDTO toDTO(Subscriptie subscriptie) {
        return new SubscriptieDTO(
                subscriptie.getId(),
                subscriptie.getFirstname(),
                subscriptie.getLastname(),
                subscriptie.getEmail(),
                subscriptie.getPhonenumber()
        );
    }

    public Subscriptie toEntity(SubscriptieDTO subscriptieDTO) {
        Subscriptie subscriptie = new Subscriptie();
        subscriptie.setId(subscriptieDTO.getId());
        subscriptie.setFirstname(subscriptieDTO.getFirstname());
        subscriptie.setLastname(subscriptieDTO.getLastname());
        subscriptie.setEmail(subscriptieDTO.getEmail());
        subscriptie.setPhonenumber(subscriptieDTO.getPhonenumber());
        return subscriptie;
    }

}

/* Mapper gebruiken in je controller zo doen ze het in de praktijk
die DTO kopieren en plakken  in chatgbt/ copilot /
 en dan gaan we die entity class nemen en het ook plakken in chatgbt of copilot
 want we willen een mapping doen tussen die entity en die dingen
 hoe map je een DTO naar deze entity en dan creert chatgbt of copilot een subscriptionMapper
 wat doet die mappers ??*/

