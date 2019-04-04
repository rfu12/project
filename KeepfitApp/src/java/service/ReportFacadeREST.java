/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import application.Report;
import application.Users;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Path("application.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "KeepfitAppPU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
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
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByReportId/{reportId}")
    @Produces({"application/json"})
    public List<Report> findByStepsPerMile(@PathParam("reportId") Integer reportId) {
    Query query = em.createNamedQuery("Report.findByReportId");
    query.setParameter("reportId", reportId);
    return query.getResultList();
    }
    
    @GET
    @Path("findByCaloriesGoal/{caloriesGoal}")
    @Produces({"application/json"})
    public List<Report> findByCaloriesGoal(@PathParam("caloriesGoal") Integer caloriesGoal) {
    Query query = em.createNamedQuery("Report.findByCaloriesGoal");
    query.setParameter("caloriesGoal", caloriesGoal);
    return query.getResultList();
    }
    
    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Report> findByDate(@PathParam("date") String date) throws ParseException {
    Query query = em.createNamedQuery("Report.findByDate"); 
    SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateObj = oDateFormat.parse(date+"T00:00:00+10:00");
    query.setParameter("date", dateObj);
    return query.getResultList();
    }
    @GET
    @Path("findByTotalCaloriesConsumed/{totalCaloriesConsumed}")
    @Produces({"application/json"})
    public List<Report> findByTotalCaloriesConsumed(@PathParam("totalCaloriesConsumed") Integer totalCaloriesConsumed) {
    Query query = em.createNamedQuery("Report.findByTotalCaloriesConsumed");
    query.setParameter("totalCaloriesConsumed", totalCaloriesConsumed);
    return query.getResultList();
    }
    @GET
    @Path("findByTotalCaloriesBurned/{totalCaloriesBurned}")
    @Produces({"application/json"})
    public List<Report> findByTotalCaloriesBurned(@PathParam("totalCaloriesBurned") Integer totalCaloriesBurned) {
    Query query = em.createNamedQuery("Report.findByTotalCaloriesBurned");
    query.setParameter("totalCaloriesBurned", totalCaloriesBurned);
    return query.getResultList();
    }
    @GET
    @Path("findByTotalStepsTaken/{totalStepsTaken}")
    @Produces({"application/json"})
    public List<Report> findByTotalStepsTaken(@PathParam("totalStepsTaken") Integer totalStepsTaken) {
    Query query = em.createNamedQuery("Report.findByTotalStepsTaken");
    query.setParameter("totalStepsTaken", totalStepsTaken);
    return query.getResultList();
    }
    @GET
    @Path("findByReportUserId/{userId}")
    @Produces({"application/json"})
    public List<Report> findByReportUserId(@PathParam("userId") Integer userId) {
    TypedQuery<Report> q = em.createQuery("SELECT s FROM Report s WHERE s.userId.userId = :userId", Report.class);
    q.setParameter("userId", userId);
    return q.getResultList();
    }
    
    @GET
    @Path("goal_reuslt/{userId}/{date}")
    @Produces({"application/json"})
    public  String  goal_reuslt(@PathParam("userId") Integer userId,@PathParam("date") String date) throws ParseException {
   // Consumption person = (Consumption) getEntityManager().createNamedQuery("Consumption.findByUserId").setParameter("userId", userId).getSingleResult();
   SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
   Date dateObj = oDateFormat.parse(date); 
   TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userId.userId=:userId and r.date=:date", Report.class);
   q.setParameter("userId", userId);
   q.setParameter("date", dateObj);
   
   System.out.println(q.getResultList());
   q.getResultList().size();
  
   List goal_reuslt=new ArrayList();
   int total_calories_consumed=0;
   int total_calories_burned=0;
   int remaining_calorie=0;
   for (int x=0;x<q.getResultList().size();x++){
       total_calories_consumed+=q.getResultList().get(x).getTotalCaloriesBurned();
       total_calories_burned+=q.getResultList().get(x).getTotalCaloriesConsumed();
      
    }
    int setgoal=q.getResultList().get(0).getCaloriesGoal();
    remaining_calorie=setgoal-(total_calories_consumed-total_calories_burned);
    
    goal_reuslt.add(total_calories_consumed);
    goal_reuslt.add(total_calories_burned);
    goal_reuslt.add(remaining_calorie);
    //List<Object[]> q = em.createQuery("SELECT s.totalCaloriesConsumed FROM Report AS s where s.userId.userId=:userId and s.date=:date", Object[].class).getResultList();   
   return goal_reuslt.toString();

    }
    @GET
    @Path("commonReport/{userId}/{startDate}/{endDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Report calorieReport(@PathParam("userId") Integer userId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) throws ParseException {
        Map<String, Object> returnMap = new HashMap<>();
        Report returnReport = new Report();
        returnMap.put("retCode", 500);
        returnMap.put("retMes", "UNKNOW ERROR");
        returnMap.put("retObj", returnReport);
        TypedQuery<Report> reportQuery = em.createQuery("SELECT r FROM Report r WHERE 1=1 AND r.userId = :userId AND r.date BETWEEN :startDate AND :endDate", Report.class);
        Users user = em.find(Users.class, userId);
        
        SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj1 = oDateFormat.parse(startDate); 
        Date dateObj2 = oDateFormat.parse(endDate); 
        
        
        
        reportQuery.setParameter("userId", user);
        reportQuery.setParameter("startDate", dateObj1);
        reportQuery.setParameter("endDate", dateObj2);
        
        System.out.println(reportQuery.getResultList().toString());
        
        int caloriesBurned = 0;
        int caloriesConsumed = 0;
        int stepsTaken = 0;
        
        List<Report> reportList = reportQuery.getResultList();
        
        System.out.println(returnReport.toString());
        if(reportList == null) {
            returnMap.put("retCode", 400);
            returnMap.put("retMes", "NO DATA");
        }
        
        
        for(Report report : reportList) {
            caloriesBurned += report.getTotalCaloriesBurned();
            caloriesConsumed += report.getTotalCaloriesConsumed();
            stepsTaken += report.getTotalStepsTaken();
        }
        returnReport.setTotalCaloriesBurned(caloriesBurned);
        
        System.out.println(returnReport);
        
        returnReport.setTotalCaloriesConsumed(caloriesConsumed);
        returnReport.setTotalStepsTaken(stepsTaken);
        returnMap.put("retCode", 200);
        returnMap.put("retMes", "");
        returnMap.put("retObj", returnReport);
        return returnReport;
    }
    
}
