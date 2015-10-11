package com.realdolmen.rdtravel.login;

import com.realdolmen.rdtravel.domain.Customer;
import com.realdolmen.rdtravel.domain.PartnerAdmin;
import com.realdolmen.rdtravel.domain.RDTravelAdmin;
import com.realdolmen.rdtravel.domain.User;
import com.realdolmen.rdtravel.principals.CustomerPrincipal;
import com.realdolmen.rdtravel.principals.PartnerAdminPrincipal;
import com.realdolmen.rdtravel.principals.RDTravelAdminPrincipal;
import com.realdolmen.rdtravel.principals.UserPrincipal;
import com.realdolmen.rdtravel.util.PasswordHash;
import org.jboss.security.PicketBoxMessages;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.AbstractServerLoginModule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.acl.Group;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by DWSAX40 on 8/10/2015.
 */
public class LoginModule extends AbstractServerLoginModule {
    @PersistenceContext private EntityManager em;

    private Principal principal;

    @Override
    public boolean login() throws LoginException {
        if(em == null) {
            CdiHelper.inject(LoginModule.class, this);
        }

        String[] info = getEmailAndPassword();
        String email = info[0];
        String password = info[1];

        this.loginOk = false;

        List<User> resultList = em.createNamedQuery(User.FIND_BY_EMAIL, User.class).setParameter("email", email).getResultList();
        if(resultList.size() == 0) {
            throw PicketBoxMessages.MESSAGES.noMatchingUsernameFoundInPrincipals();
        }
        User user = resultList.get(0);

        if(principal == null) {
            if(user instanceof Customer)
                principal = new CustomerPrincipal(email, (Customer)user);
            else if(user instanceof PartnerAdmin)
                principal = new PartnerAdminPrincipal(email, (PartnerAdmin)user);
            else if(user instanceof RDTravelAdmin)
                principal = new RDTravelAdminPrincipal(email, (RDTravelAdmin)user);
        }

        if(!validatePassword(password, user.getPassword())) {
            throw PicketBoxMessages.MESSAGES.invalidPassword();
        }

        this.loginOk = true;

        return true;
    }

    @Override
    protected Principal getIdentity() {
        return principal;
    }

    protected boolean validatePassword(String inputPassword, String expectedPassword) {
        try {
            return PasswordHash.validatePassword(inputPassword, expectedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error occurred while validating password.");
        }
    }

    protected String createPasswordHash(String password) throws LoginException {
        try {
            return PasswordHash.createHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new LoginException("Error occurred while hashing password.");
        }
    }

    @Override
    protected Group[] getRoleSets() throws LoginException {
        SimpleGroup group = new SimpleGroup("Roles");

        UserPrincipal userPrincipal = (UserPrincipal)principal;
        group.addMember(new SimplePrincipal(userPrincipal.getRoleName()));

        return new Group[] { group };
    }

    /** Called by login() to acquire the email and password strings for
     authentication. This method does no validation of either.
     @return String[], [0] = username, [1] = password
     @exception LoginException thrown if CallbackHandler is not set or fails.
     */
    protected String[] getEmailAndPassword() throws LoginException {
        String[] info = {null, null};
        // prompt for a username and password
        if(callbackHandler == null) {
            throw PicketBoxMessages.MESSAGES.noCallbackHandlerAvailable();
        }

        NameCallback nc = new NameCallback(PicketBoxMessages.MESSAGES.enterUsernameMessage(), "guest");
        PasswordCallback pc = new PasswordCallback(PicketBoxMessages.MESSAGES.enterPasswordMessage(), false);
        Callback[] callbacks = {nc, pc};
        String email = null;
        String password = null;
        try {
            callbackHandler.handle(callbacks);
            email = nc.getName();
            char[] tmpPassword = pc.getPassword();
            if( tmpPassword != null )
            {
                char[] credential = new char[tmpPassword.length];
                System.arraycopy(tmpPassword, 0, credential, 0, tmpPassword.length);
                pc.clearPassword();
                password = new String(credential);
            }
        } catch(IOException e) {
            LoginException le = PicketBoxMessages.MESSAGES.failedToInvokeCallbackHandler();
            le.initCause(e);
            throw le;
        } catch(UnsupportedCallbackException e) {
            LoginException le = new LoginException();
            le.initCause(e);
            throw le;
        }

        info[0] = email;
        info[1] = password;

        return info;
    }
}
