package org.apereo.cas.config;

import org.apereo.cas.api.AuthenticationRiskEvaluator;
import org.apereo.cas.api.AuthenticationRiskMitigator;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.RiskAwareAuthenticationWebflowConfigurer;
import org.apereo.cas.web.flow.RiskAwareAuthenticationWebflowEventResolver;
import org.apereo.cas.web.flow.resolver.CasDelegatingWebflowEventResolver;
import org.apereo.cas.web.flow.resolver.CasWebflowEventResolver;
import org.apereo.cas.web.flow.resolver.impl.CasWebflowEventResolutionConfigurationContext;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * This is {@link ElectronicFenceWebflowConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
@Configuration(value = "electronicFenceWebflowConfiguration", proxyBeanMethods = false)
@EnableConfigurationProperties(CasConfigurationProperties.class)
@EnableScheduling
public class ElectronicFenceWebflowConfiguration {

    @ConditionalOnMissingBean(name = "riskAwareAuthenticationWebflowEventResolver")
    @Bean
    @RefreshScope
    @Autowired
    public CasWebflowEventResolver riskAwareAuthenticationWebflowEventResolver(
        @Qualifier("casWebflowConfigurationContext")
        final CasWebflowEventResolutionConfigurationContext casWebflowConfigurationContext,
        @Qualifier("authenticationRiskMitigator")
        final AuthenticationRiskMitigator authenticationRiskMitigator,
        @Qualifier("authenticationRiskEvaluator")
        final AuthenticationRiskEvaluator authenticationRiskEvaluator,
        @Qualifier("initialAuthenticationAttemptWebflowEventResolver")
        final CasDelegatingWebflowEventResolver initialAuthenticationAttemptWebflowEventResolver) {
        val r = new RiskAwareAuthenticationWebflowEventResolver(casWebflowConfigurationContext,
            authenticationRiskEvaluator, authenticationRiskMitigator);
        initialAuthenticationAttemptWebflowEventResolver.addDelegate(r, 0);
        return r;
    }

    @ConditionalOnMissingBean(name = "riskAwareAuthenticationWebflowConfigurer")
    @Bean
    @RefreshScope
    @Autowired
    public CasWebflowConfigurer riskAwareAuthenticationWebflowConfigurer(
        final ConfigurableApplicationContext applicationContext,
        final CasConfigurationProperties casProperties,
        @Qualifier("loginFlowRegistry")
        final FlowDefinitionRegistry loginFlowDefinitionRegistry,
        @Qualifier("flowBuilderServices")
        final FlowBuilderServices flowBuilderServices) {
        return new RiskAwareAuthenticationWebflowConfigurer(flowBuilderServices,
            loginFlowDefinitionRegistry, applicationContext, casProperties);
    }

    @Bean
    @RefreshScope
    @Autowired
    @ConditionalOnMissingBean(name = "riskAwareCasWebflowExecutionPlanConfigurer")
    public CasWebflowExecutionPlanConfigurer riskAwareCasWebflowExecutionPlanConfigurer(
        @Qualifier("riskAwareAuthenticationWebflowConfigurer")
        final CasWebflowConfigurer riskAwareAuthenticationWebflowConfigurer) {
        return plan -> plan.registerWebflowConfigurer(riskAwareAuthenticationWebflowConfigurer);
    }
}
