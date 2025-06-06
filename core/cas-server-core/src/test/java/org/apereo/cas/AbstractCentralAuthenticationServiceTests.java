package org.apereo.cas;

import org.apereo.cas.authentication.AuthenticationManager;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.CoreAuthenticationTestUtils;
import org.apereo.cas.authentication.attribute.AttributeDefinitionStore;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.ticket.registry.TicketRegistrySupport;
import org.apereo.cas.validation.ServiceTicketValidationAuthorizersExecutionPlan;
import org.apereo.cas.web.cookie.CasCookieBuilder;
import org.apereo.cas.web.support.ArgumentExtractor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;

/**
 * @author Scott Battaglia
 * @since 3.0.0
 */
@TestPropertySource(properties = {
    "cas.authn.policy.any.try-all=true",
    "cas.ticket.st.time-to-kill-in-seconds=30"
})
@Setter
@Getter
public abstract class AbstractCentralAuthenticationServiceTests extends BaseCasCoreTests {
    @Autowired
    @Qualifier("serviceValidationAuthorizers")
    private ServiceTicketValidationAuthorizersExecutionPlan serviceValidationAuthorizers;

    @Autowired
    @Qualifier(CentralAuthenticationService.BEAN_NAME)
    private CentralAuthenticationService centralAuthenticationService;

    @Autowired
    @Qualifier(CasCookieBuilder.BEAN_NAME_TICKET_GRANTING_COOKIE_BUILDER)
    private CasCookieBuilder ticketGrantingTicketCookieGenerator;

    @Autowired
    @Qualifier(TicketRegistry.BEAN_NAME)
    private TicketRegistry ticketRegistry;

    @Autowired
    @Qualifier(AuthenticationManager.BEAN_NAME)
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier(ServicesManager.BEAN_NAME)
    private ServicesManager servicesManager;

    @Autowired
    @Qualifier(ArgumentExtractor.BEAN_NAME)
    private ArgumentExtractor argumentExtractor;

    @Autowired
    @Qualifier(TicketRegistrySupport.BEAN_NAME)
    private TicketRegistrySupport ticketRegistrySupport;

    @Autowired
    @Qualifier(WebApplicationService.BEAN_NAME_FACTORY)
    private ServiceFactory<WebApplicationService> webApplicationServiceFactory;

    @Autowired
    @Qualifier(PrincipalFactory.BEAN_NAME)
    private PrincipalFactory principalFactory;

    @Autowired
    @Qualifier(AuthenticationSystemSupport.BEAN_NAME)
    private AuthenticationSystemSupport authenticationSystemSupport;

    @Autowired
    @Qualifier(PrincipalResolver.BEAN_NAME_PRINCIPAL_RESOLVER)
    private PrincipalResolver defaultPrincipalResolver;

    @Autowired
    @Qualifier(AttributeDefinitionStore.BEAN_NAME)
    private AttributeDefinitionStore attributeDefinitionStore;

    @BeforeEach
    void before() {
        val request = new MockHttpServletRequest();
        request.setRemoteAddr("223.456.789.000");
        request.setLocalAddr("223.456.789.100");
        request.addHeader(HttpHeaders.USER_AGENT, "Firefox");
        ClientInfoHolder.setClientInfo(ClientInfo.from(request));
    }

    protected Service getService(final String name) {
        val request = new MockHttpServletRequest();
        request.addParameter(CasProtocolConstants.PARAMETER_SERVICE, name);
        return getWebApplicationServiceFactory().createService(request);
    }

    protected Service getService() {
        return getService(CoreAuthenticationTestUtils.CONST_TEST_URL);
    }
}
