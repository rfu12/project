/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import application.Consumption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Raymondsama
 */
@Stateless
@Path("application.consumption")
public class ConsumptionFacadeREST extends AbstractFacade<Consumption> {

    @PersistenceContext(unitName = "KeepfitAppPU")
    private EntityManager em;

    public ConsumptionFacadeREST() {
        super(Consumption.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consumption entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Consumption entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Consumption find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    @GET
    @Path("findByConsumptionId/{consumptionId}")
    @Produces({"application/json"})
    public List<Consumption> findByConsumptionId(@PathParam("consumptionId") Integer consumptionId) {
    Query query = em.createNamedQuery("Consumption.findByConsumptionId");
    query.setParameter("consumptionId", consumptionId);
    return query.getResultList();
    }
    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Consumption> findByDate(@PathParam("date") String date) throws ParseException {
    Query query = em.createNamedQuery("Consumption.findByDate");   
    SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateObj = oDateFormat.parse(date+"T00:00:00+10:00");    
    query.setParameter("date", dateObj);
    return query.getResultList();
    }
    @GET
    @Path("findByQuantity/{quantity}")
    @Produces({"application/json"})
    public List<Consumption> findByQuantity(@PathParam("quantity") Integer quantity) {
    Query query = em.createNamedQuery("Consumption.findByQuantity");
    query.setParameter("quantity", quantity);
    return query.getResultList();
    }
    @GET
    @Path("findByUserId/{userId}")
    @Produces({"application/json"})
    public List<Consumption> findByUserId(@PathParam("userId") Integer userId) {
    TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.userId.userId = :userId", Consumption.class);
    q.setParameter("userId", userId);
    return q.getResultList();
    }
    @GET
    @Path("findByFoodId/{foodId}")
    @Produces({"application/json"})
    public List<Consumption> findByFoodId(@PathParam("foodId") Integer foodId) {
    TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.foodId.foodId = :foodId", Consumption.class);
    q.setParameter("foodId", foodId);
    return q.getResultList();
    }
    @GET
    @Path("total_calories_consumed/{userId}/{date}")
    @Produces({"text/plain"})
    public int total_calories_consumed(@PathParam("userId") Integer userId,@PathParam("date") String date) throws ParseException {
   // Consumption person = (Consumption) getEntityManager().createNamedQuery("Consumption.findByUserId").setParameter("userId", userId).getSingleResult();
    TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.userId.userId = :userId and s.date= :date", Consumption.class);
    SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateObj = oDateFormat.parse(date);  
    
    //int size=q.getResultList().size();
    
    q.setParameter("userId", userId);
    q.setParameter("date", dateObj);
  
    int output=0;
    
    for (int x=0;x<q.getResultList().size();x++){
      int CalorieAmount=q.getResultList().get(x).getFoodId().getCalorieAmount();
      //System.out.println(CalorieAmount);
      int number=q.getResultList().get(x).getQuantity();
      //System.out.println(number);
      output+=CalorieAmount*number;
      
    }
 
    return output; 
    }
    
}
