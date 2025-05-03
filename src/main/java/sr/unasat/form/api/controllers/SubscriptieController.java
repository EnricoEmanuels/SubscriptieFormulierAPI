package sr.unasat.form.api.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import sr.unasat.form.api.dto.SubscriptieDTO;
import sr.unasat.form.api.entities.Subscriptie;
import sr.unasat.form.api.mappers.SubscriptieMapper;
import sr.unasat.form.api.services.SubscriptieService;

@Path("/subscriptions")
public class SubscriptieController {
    private SubscriptieService subscriptieService;
    private SubscriptieMapper subscriptieMapper; /* je zet het als een data member / eigenschap omdat je het in die hele controller class wilt gebruiken / we willen het niet steeds opnieuw aanmaken in paramaters  */

    private static final List<SubscriptieDTO> subscriptieDTOs = new ArrayList<>();

    public SubscriptieController(SubscriptieService subscriptieService, SubscriptieMapper subscriptieMapper) {
        this.subscriptieService = subscriptieService;
        this.subscriptieMapper = subscriptieMapper;
    }

//    public void setSubscriptieService(SubscriptieService subscriptieService) {
//        this.subscriptieService = subscriptieService;
//    }

//    public SubscriptieController() {
//
//    }


    /* stap 1 van programmeren is je moet je stappen uitschrijven stap 2 dan kan je gericht zoeken   */
    /* je moet eerst al je doelen schrijven en dan begrijpen en dan dan pas die implementatie doen    */
    /* de snelheid waarmee je leert is ook belangrijk je hoef niet de snelste te zjn maar je moet wel goed leren    */



    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Subscriptie> getSubscripties() {
        return subscriptieService.getAllsubscrripties();
    }

    /* // werkt perfect
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getsubscriptieById(@PathParam("id") int id) {
        Subscriptie subscriptie = subscriptieService.findSubscriptieById(id);
        if (subscriptie != null) {
            return Response.ok(subscriptie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    */

    /* wanneer je een API bouwt kan je een keuze maken of je jouw custom message/ berich in je backend p
    laatsen(zoasls bij die get (id))
    of je kan het opvangen in je frontend  */

    /* Als ik een API heb en iedereen mag zijn eigen client erop bouwen dan kan ik het sturen of in die backend zetten
    die ene kan een ANdriod application zijn die andere een WEBAPP die andere een IOS APP dus als ze die aanroep doen
     dan is die custom bericht er al . Maar dat is een design keuze die je gaat maken op basis van je klass
     waar wil je die error messages onderhouden wil je het in je API onderhouden of je client / frontend*/

    /* die methode in response entity() dit stuurt gewoon data hij gaat de data terug sturen
    je stuurt het via een DTO inplaats van een entity dit maak het veiliger  */

    /* die entity() includeerdt een bericht of een object ( als we die object hebben gevonden kunnen we die object retourneren)
    als we de object niet hebben gevonden kunnen we tekst retourneren
    maar als je JSON wilt retournern dat moet je het doen met HASHMAP want JSON werkt met key/ value pairs
    voor key values gebruik je in java een MAP (interface)
     */

    /* 2de stap is spuedo code dat is een stap verder daar ga je wel praten in terme van programmeren
    maar je hebt nog geen taal of syntax vastgesteld  */

    /* stap 1 kon pseudo code zijn : Controleer In de database.Of de subscription record voorkomt op basis van ID.
    of zoek in de database naar de subscriptie record mdt de aangegeven ID  */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptieById(@PathParam("id") int id) {
        // stap 1 zoeken naar een beppalde ID
        Subscriptie subscriptie = subscriptieService.findSubscriptieById(id);
         // stap 2 als de ID niet bestaat retourneer not found
        if (subscriptie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
//            Map<String, String> responseObj = new HashMap<>();
//            responseObj.put("message", "Subscription not found with id: " + id);
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity(responseObj)
//                    .build();

        }
         // stap 3 indien gevonden mappen naar DTO en retouneren

        SubscriptieDTO subscriptieDTO = subscriptieMapper.toDTO(subscriptie);
        return Response.status(Response.Status.OK).entity(subscriptieDTO).build();
    }




    /*
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSubscriptie(Subscriptie subscriptie) {
        SubscriptieService saved = subscriptieService.saveSubscriptie(subscriptie);
        return Response.ok(saved).build();
    }

     */

    // dit werkt

//    @POST
//    @Path("/create")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Subscriptie createSubscriptie(Subscriptie subscriptie) {
//        subscriptieService.saveSubscriptie(subscriptie);
//        return subscriptie;
//    }



    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSubscriptie(Subscriptie subscriptie) {
        Subscriptie subscriptieopslaan = subscriptieService.saveSubscriptie(subscriptie);
        SubscriptieDTO subscriptieDTO = subscriptieMapper.toDTO(subscriptieopslaan);
        return Response.status(Response.Status.OK).entity(subscriptieDTO).build();
    }


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSubscriptie(@PathParam("id") int id, Subscriptie updatedSubscriptie) {
        Subscriptie existingSubscription = subscriptieService.findSubscriptieById(id);
        if (existingSubscription != null) {
            existingSubscription.setFirstname(updatedSubscriptie.getFirstname());
            existingSubscription.setLastname(updatedSubscriptie.getLastname());
            existingSubscription.setEmail(updatedSubscriptie.getEmail());
            existingSubscription.setPhonenumber(updatedSubscriptie.getPhonenumber());
            subscriptieService.updateSubscriptie(existingSubscription);
            SubscriptieDTO subsriptieDTO = subscriptieMapper.toDTO(existingSubscription);
            return Response.ok(existingSubscription).entity(subsriptieDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubscriptie(@PathParam("id") int id) {
        Subscriptie existingSubscriptie = subscriptieService.findSubscriptieById(id);

        if (existingSubscriptie != null) {
            subscriptieService.deleteSubscriptie(id);

            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}



/* in je controllers moet je nouns(zelfstandige naamwooorden gebruiken in plaats van werkwoorden) */

/* We can identify “customers” collection resource using the URI “/customers“.
We can identify a single “customer” resource using the URI “/customers/{customerId}“.
 */

/* voorbeeld een customer kan verschillende accounts hebben dan moet je subclassen
maken ervan hoe moet ik dit doen
dit lijk handig en interesant om een specifieke account te halen

For example, sub-collection resource “accounts” of a particular “customer” can
be identified using the URN “/customers/{customerId}/accounts” (in a banking domain).

Similarly, a singleton resource “account” inside the sub-collection resource “accounts” can
be identified as follows: “/customers/{customerId}/accounts/{accountId}“. */

/* best practices een ander naam voor controllers zijn resources

2.1. Use nouns to represent resources (zelfstandige naamwoorden )

2.1.1. document
A document resource is a singular concept that is akin to an object instance or database record.

In REST, you can view it as a single resource inside a resource collection. A document’s state representation
typically includes both fields with values and links to other related resources.

Use the “singular” name to denote the document resource archetype.

http://api.example.com/device-management/managed-devices/{device-id}
http://api.example.com/user-management/users/{id}
http://api.example.com/user-management/users/admin

 */

/* 2.2.3. Use hyphens (-) to improve the readability of URIs */

/* 2.2.4. Do not use underscores ( _ ) */

/* http://api.example.com/inventory-management/managed-entities/{id}/install-script-location  //More readable

http://api.example.com/inventory-management/managedEntities/{id}/installScriptLocation  //Less readable

 */

/* 2.2.5. Use lowercase letters in URIs */




// Hotel - Management systeem
/*

1. kamers ( poor(300usd (1 bed ) ) , middle class(600usd) ()2 bedden , extra rich (1200) (4 bedden)  )
2. reservatie (kamers die beschikbaarzijn)
3. betaalmethode
4. registratie (id ( sociaal security number ), naam voornaam )

*/



