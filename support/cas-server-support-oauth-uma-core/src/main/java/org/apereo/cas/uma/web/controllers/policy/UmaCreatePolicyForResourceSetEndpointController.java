package org.apereo.cas.uma.web.controllers.policy;

import org.apereo.cas.support.oauth.OAuth20Constants;
import org.apereo.cas.uma.UmaConfigurationContext;
import org.apereo.cas.uma.ticket.resource.ResourceSetPolicy;
import org.apereo.cas.uma.web.controllers.BaseUmaEndpointController;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.LoggingUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hjson.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * This is {@link UmaCreatePolicyForResourceSetEndpointController}.
 *
 * @author Misagh Moayyed
 * @since 6.0.0
 */
@Slf4j
@Tag(name = "User Managed Access")
public class UmaCreatePolicyForResourceSetEndpointController extends BaseUmaEndpointController {

    public UmaCreatePolicyForResourceSetEndpointController(final UmaConfigurationContext umaConfigurationContext) {
        super(umaConfigurationContext);
    }

    /**
     * Gets policy for resource set.
     *
     * @param resourceId the resource id
     * @param body       the body
     * @param request    the request
     * @param response   the response
     * @return the policy for resource set
     */
    @PostMapping(OAuth20Constants.BASE_OAUTH20_URL + "/{resourceId}/" + OAuth20Constants.UMA_POLICY_URL)
    @Operation(summary = "Create policy for resource set", description = "Creates a policy for the specified resource set",
        parameters = @Parameter(name = "resourceId", required = true, in = ParameterIn.PATH, description = "Resource ID"))
    public ResponseEntity createPolicyForResourceSet(
        @PathVariable("resourceId")
        final long resourceId,
        @RequestBody
        final String body,
        final HttpServletRequest request,
        final HttpServletResponse response) {
        try {
            val profileResult = getAuthenticatedProfile(request, response, OAuth20Constants.UMA_PROTECTION_SCOPE);
            val resourceSetResult = getUmaConfigurationContext().getUmaResourceSetRepository().getById(resourceId);
            if (resourceSetResult.isEmpty()) {
                val model = buildResponseEntityErrorModel(HttpStatus.NOT_FOUND, "Requested resource-set cannot be found");
                return new ResponseEntity(model, model, HttpStatus.BAD_REQUEST);
            }
            val resourceSet = resourceSetResult.get();
            resourceSet.validate(profileResult);

            val umaRequest = MAPPER.readValue(JsonValue.readHjson(body).toString(), ResourceSetPolicy.class);
            resourceSet.getPolicies().add(umaRequest);
            val saved = Objects.requireNonNull(getUmaConfigurationContext().getUmaResourceSetRepository().save(resourceSet));

            val model = CollectionUtils.wrap("entity", saved, "code", HttpStatus.CREATED);
            return new ResponseEntity(model, HttpStatus.OK);
        } catch (final Exception e) {
            LoggingUtils.error(LOGGER, e);
        }
        return new ResponseEntity("Unable to save policy for resource-set.", HttpStatus.BAD_REQUEST);
    }
}
