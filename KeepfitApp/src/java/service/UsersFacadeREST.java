/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import application.Consumption;
import application.Users;
import static application.Users_.weight;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
@Path("application.users")
public class UsersFacadeREST extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "KeepfitAppPU")
    private EntityManager em;

    public UsersFacadeREST() {
        super(Users.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Users entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Users entity) {
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
    public Users find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Users> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Users> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByUserId/{userId}")
    @Produces({"application/json"})
    public List<Users> findByUserId(@PathParam("userId") Integer userId) {
    Query query = em.createNamedQuery("Users.findByUserId");
    query.setParameter("userId", userId);
    return query.getResultList();
    }
    @GET
    @Path("findByName/{name}")
    @Produces({"application/json"})
    public List<Users> findByName(@PathParam("name") String name) {
    Query query = em.createNamedQuery("Users.findByName");
    query.setParameter("name", name);
    return query.getResultList();
    }
    @GET
    @Path("findBySurname/{surname}")
    @Produces({"application/json"})
    public List<Users> findBySurname(@PathParam("surname") String surname) {
    Query query = em.createNamedQuery("Users.findBySurname");
    query.setParameter("surname", surname);
    return query.getResultList();
    }
    @GET
    @Path("findByEmail/{email}")
    @Produces({"application/json"})
    public List<Users> findByEmail(@PathParam("email") String email) {
    Query query = em.createNamedQuery("Users.findByEmail");
    query.setParameter("email", email);
    return query.getResultList();
    }
    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Users> findByDob(@PathParam("dob") String dob) throws ParseException {
    Query query = em.createNamedQuery("Users.findByDob");   
    SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateObj = oDateFormat.parse(dob+"T00:00:00+10:00");
    query.setParameter("dob", dateObj);
    return query.getResultList();
    }
    @GET
    @Path("findByHeight/{height}")
    @Produces({"application/json"})
    public List<Users> findByHeight(@PathParam("height") Integer height) {
    Query query = em.createNamedQuery("Users.findByHeight");
    query.setParameter("height", height);
    return query.getResultList();
    }
    @GET
    @Path("findByWeight/{weight}")
    @Produces({"application/json"})
    public List<Users> findByWeight(@PathParam("weight") Integer weight) {
    Query query = em.createNamedQuery("Users.findByWeight");
    query.setParameter("weight", weight);
    return query.getResultList();
    }
    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Users> findByGender(@PathParam("gender") String gender) {
    Query query = em.createNamedQuery("Users.findByGender");
    query.setParameter("gender", gender);
    return query.getResultList();
    }
    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})
    public List<Users> findByAddress(@PathParam("address") String address) {
    Query query = em.createNamedQuery("Users.findByAddress");
    query.setParameter("address", address);
    return query.getResultList();
    }
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<Users> findByPostcode(@PathParam("postcode") Integer postcode) {
    Query query = em.createNamedQuery("Users.findByPostcode");
    query.setParameter("postcode", postcode);
    return query.getResultList();
    }
    @GET
    @Path("findByLevelOfActivity/{levelOfActivity}")
    @Produces({"application/json"})
    public List<Users> findByLevelOfActivity(@PathParam("levelOfActivity") Integer levelOfActivity) {
    Query query = em.createNamedQuery("Users.findByLevelOfActivity");
    query.setParameter("levelOfActivity", levelOfActivity);
    return query.getResultList();
    }
    @GET
    @Path("findByStepsPerMile/{stepsPerMile}")
    @Produces({"application/json"})
    
    public List<Users> findByStepsPerMile(@PathParam("stepsPerMile") Integer stepsPerMile) {
    Query query = em.createNamedQuery("Users.findByStepsPerMile");
    query.setParameter("stepsPerMile", stepsPerMile);
    return query.getResultList();
    }
    
    @GET
    @Path("burned_per_step/{userId}")
    @Produces({"text/plain"})
    public double burned_per_step(@PathParam("userId") Integer userId) {
    Users person = (Users) getEntityManager().createNamedQuery("Users.findByUserId").setParameter("userId", userId).getSingleResult();
    
    int weight=person.getWeight();
    int stepspermile=person.getStepsPerMile();

    if (stepspermile !=0) {
        double result=weight*0.49/stepspermile;
        return result;
        } 
    else {
        return 0;
        } 
    }
    
    //@GET
    //@Path("BMR_calculation/{userId}")
    //@Produces({"text/plain"})
   // public Integer BMR_calculation(@PathParam("userId") Integer userId) {
   // Users person = (Users) getEntityManager().createNamedQuery("Users.findByUserId").setParameter("userId", userId).getSingleResult();
    
   // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
   // Date date=person.getDob();
   // simpleDateFormat = new SimpleDateFormat("YYYY");
    //System.out.println(simpleDateFormat.format(date));
    //String year=data
  //  int year=Integer.valueOf(simpleDateFormat.format(date));
  //  int age=2019-year;
   // int weight=person.getWeight();
  //  int height=person.getHeight();

  //  return null; 
  //  }
    
    @GET
    @Path("BMR_calculation/{userId}")
    @Produces({"text/plain"})
    public double BMR_calculation(@PathParam("userId") Integer userId) {
    Users person = (Users) getEntityManager().createNamedQuery("Users.findByUserId").setParameter("userId", userId).getSingleResult();
    
        Calendar cal = Calendar.getInstance();
        Date birthday=person.getDob();
        if(birthday == null) throw new IllegalArgumentException("Birthday shouldn't be null!");
        if(birthday.after(cal.getTime())) throw new IllegalArgumentException("Birthday is after now.He or she must be still a sperm");
        int ynow = cal.get(Calendar.YEAR);
        int mnow = cal.get(Calendar.MONTH);
        int dnow = cal.get(Calendar.DAY_OF_MONTH);    
        cal.setTime(birthday);
        int ydob = cal.get(Calendar.YEAR);
         int mdob = cal.get(Calendar.MONTH);
        int ddob = cal.get(Calendar.DAY_OF_MONTH);    
        int age = ynow - ydob;
        if(mnow > mdob) age--;
        else if(mnow == mdob){
            if(dnow > ddob) age--;
        }
        if(person.getWeight() == null) throw new IllegalArgumentException("Weight shouldn't be null!");       
        if(person.getHeight() == null) throw new IllegalArgumentException("Height shouldn't be null!");
        double weight=person.getWeight() * 0.454;
        double height=person.getHeight();
        double output;
        if(person.getGender().toLowerCase()=="male"){
             output = (13.75 * weight) + (5.003 * height) - (6.755 * age) + 66.5;
        }
        else{
             output = (9.563* weight) + (1.85 * height) - (4.676 * age) + 655.1;
        }
        
        return output;
    }
    
    @GET
    @Path("total_calories_burned/{userId}")
    @Produces({"text/plain"})
    public double total_calories_burned(@PathParam("userId") Integer userId) {
    Users person = (Users) getEntityManager().createNamedQuery("Users.findByUserId").setParameter("userId", userId).getSingleResult();
    double bmr=BMR_calculation(userId);
    double result=0;
    if (person.getLevelOfActivity()==1) {
        result=bmr*1.2;
        
        } 
    else if(person.getLevelOfActivity()==2) {
        result=bmr*1.375;
        }
    else if(person.getLevelOfActivity()==3) {
        result=bmr*1.55;
        }
    else if(person.getLevelOfActivity()==4) {
        result=bmr*1.725;
        }
    else if(person.getLevelOfActivity()==5) {
        result=bmr*1.9;
        }  
    return result; 
    }
  
}
