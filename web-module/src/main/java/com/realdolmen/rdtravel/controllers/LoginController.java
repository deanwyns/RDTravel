package com.realdolmen.rdtravel.controllers;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * Created by JSTAX29 on 5/10/2015.
 */
@Named
@RequestScoped
public class LoginController {
    private String username;
    private String password;
    private String redirect;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String login() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.username, this.password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            return "error";
        }

        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().redirect("../index.xhtml");

        return "index";
    }

    public void logout() throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            ctx.addMessage(null, new FacesMessage("Logout failed."));
        }

        Map<String, String> params = ctx.getExternalContext().getRequestParameterMap();
        if(params.containsKey("redirect")) {
            ctx.getExternalContext().redirect(params.get("redirect"));
        }
    }
}
