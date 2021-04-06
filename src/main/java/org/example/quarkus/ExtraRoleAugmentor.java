package org.example.quarkus;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ExtraRoleAugmentor implements SecurityIdentityAugmentor {

    @Inject
    ExtraRoleSupplier supplier;

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public Uni<SecurityIdentity> augment(final SecurityIdentity identity, final AuthenticationRequestContext context) {
        // Not using because we will query database (blocking IO)
        // return Uni.createFrom().item(build(identity));
        supplier.setIdentity(identity);
        return context.runBlocking(supplier);
    }

}
