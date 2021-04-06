package org.example.quarkus;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import org.jboss.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Dependent
public class ExtraRoleSupplier implements Supplier<SecurityIdentity> {

    Logger logger = Logger.getLogger(ExtraRoleSupplier.class);

    private SecurityIdentity identity;

    @ActivateRequestContext
    @Transactional
    @Override
    public SecurityIdentity get() {
        //  Commented for the reproducer
        // if (identity.isAnonymous()) {
        //      return () -> identity;
        // }
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder(identity);
        // Let's pretend it's business logic, involving blocking IO and EntityManager
        logger.infof("Adding Extra role");
        builder.addRole(RestResource.EXTRA);
        return builder.build();
    }

    public void setIdentity(final SecurityIdentity identity) {
        this.identity = identity;
    }

}