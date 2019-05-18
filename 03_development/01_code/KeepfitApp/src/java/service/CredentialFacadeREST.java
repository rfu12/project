/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import application.Credential;
import application.Users;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.RegisterFormModel;
import util.EncryptionUtil;

/**
 *
 * @author Raymondsama
 */
@Stateless
@Path("application.credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @EJB
    private UsersFacadeREST usersFacadeREST;

    @PersistenceContext(unitName = "KeepfitAppPU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Credential entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credential find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByUsername/{username}")
    @Produces({"application/json"})
    public List<Credential> findByUsername(@PathParam("username") String username) {
        Query query = em.createNamedQuery("Credential.findByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }

    @GET
    @Path("findByPasswordHash/{passwordHash}")
    @Produces({"application/json"})
    public List<Credential> findByPasswordHash(@PathParam("passwordHash") String passwordHash) {
        Query query = em.createNamedQuery("Credential.findByPasswordHash");
        query.setParameter("passwordHash", passwordHash);
        return query.getResultList();
    }

    @GET
    @Path("findBySignUpDate/{signUpDate}")
    @Produces({"application/json"})
    public List<Credential> findBySignUpDate(@PathParam("signUpDate") String signUpDate) throws ParseException {
        Query query = em.createNamedQuery("Credential.findBySignUpDate");
        SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = oDateFormat.parse(signUpDate + "T00:00:00+10:00");
        query.setParameter("signUpDate", dateObj);
        return query.getResultList();
    }

    @GET
    @Path("findByUserId/{userId}")
    @Produces({"application/json"})
    public List<Credential> findByUserId(@PathParam("userId") Integer userId) {
        TypedQuery<Credential> q = em.createQuery("SELECT s FROM Credential s WHERE s.userId.userId = :userId", Credential.class);
        q.setParameter("userId", userId);
        return q.getResultList();
    }

    @GET
    @Path("login/{userName}/{password}")
    @Produces({"application/json"})
    public List<Credential> login(@PathParam("userName") String userName, @PathParam("password") String password) {
        userName = userName.trim();
        password = password.trim();
        if (userName == null || "".equals(userName)) {
            return null;
        }
        TypedQuery<Credential> q = em.createQuery("SELECT s FROM Credential s WHERE s.username = :userName and s.passwordHash = :password", Credential.class);
        q.setParameter("userName", userName);
        q.setParameter("password", password);
        List<Credential> credList = q.getResultList();
        if (credList != null && credList.size() == 1) {
            credList.get(0).setPasswordHash(null);
            return credList;
        } else {
            return null;
        }
    }

    @POST
    @Path("register")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String register(RegisterFormModel registerFormModel) {
        Credential checkExist = find(registerFormModel.getCredential().getUsername());
        if (checkExist != null) return null;
        if (registerFormModel.getUsers().getUserId() == null) 
            registerFormModel.getUsers().setUserId(usersFacadeREST.count() + 1);
        usersFacadeREST.create(registerFormModel.getUsers());
        registerFormModel.getCredential().setSignUpDate(new Date());
        registerFormModel.getCredential().setPasswordHash(EncryptionUtil.string2MD5(registerFormModel.getCredential().getPasswordHash()));
        registerFormModel.getCredential().setUserId(registerFormModel.getUsers());
        super.create(registerFormModel.getCredential());
        return registerFormModel.getCredential().getUsername();
    }
}
