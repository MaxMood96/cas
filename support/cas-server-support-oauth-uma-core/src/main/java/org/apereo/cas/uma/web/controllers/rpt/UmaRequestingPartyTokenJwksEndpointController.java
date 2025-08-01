package org.apereo.cas.uma.web.controllers.rpt;

import org.apereo.cas.support.oauth.OAuth20Constants;
import org.apereo.cas.uma.UmaConfigurationContext;
import org.apereo.cas.uma.web.controllers.BaseUmaEndpointController;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.ResourceUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * This is {@link UmaRequestingPartyTokenJwksEndpointController}.
 *
 * @author Misagh Moayyed
 * @since 6.0.0
 */
@Slf4j
@Tag(name = "User Managed Access")
public class UmaRequestingPartyTokenJwksEndpointController extends BaseUmaEndpointController {
    public UmaRequestingPartyTokenJwksEndpointController(final UmaConfigurationContext umaConfigurationContext) {
        super(umaConfigurationContext);
    }

    /**
     * Gets JWKS used to sign RPTs.
     *
     * @param request  the request
     * @param response the response
     * @return redirect view
     */
    @GetMapping(OAuth20Constants.BASE_OAUTH20_URL + '/' + OAuth20Constants.UMA_JWKS_URL)
    @Operation(
        summary = "Get JWKS used to sign RPTs",
        description = "Returns the JWKS used to sign RPTs")
    public ResponseEntity<String> getKeys(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            val jwks = getUmaConfigurationContext().getCasProperties().getAuthn()
                .getOauth().getUma().getRequestingPartyToken().getJwksFile().getLocation();
            if (ResourceUtils.doesResourceExist(jwks)) {
                try (val is = jwks.getInputStream()) {
                    val jsonJwks = IOUtils.toString(is, StandardCharsets.UTF_8);
                    val jsonWebKeySet = new JsonWebKeySet(jsonJwks);
                    val body = jsonWebKeySet.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    return new ResponseEntity<>(body, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("UMA RPT JWKS resource is undefined or cannot be located", HttpStatus.NOT_IMPLEMENTED);
        } catch (final Exception e) {
            LoggingUtils.error(LOGGER, e);
            return new ResponseEntity<>(StringEscapeUtils.escapeHtml4(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
