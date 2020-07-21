package com.patternmatch.ecs.textprocessor.service;

import com.amazonaws.services.servicediscovery.AWSServiceDiscovery;
import com.amazonaws.services.servicediscovery.AWSServiceDiscoveryClientBuilder;
import com.amazonaws.services.servicediscovery.model.DiscoverInstancesRequest;
import com.amazonaws.services.servicediscovery.model.DiscoverInstancesResult;
import com.amazonaws.services.servicediscovery.model.HealthStatus;
import com.amazonaws.services.servicediscovery.model.HttpInstanceSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@ConditionalOnProperty(value = "servicediscovery.method", havingValue = "sdk")
public class CloudMapServiceLocationResolver implements ServiceLocationResolver {

    private static final String AWS_INSTANCE_IPV_4_ATTRIBUTE = "AWS_INSTANCE_IPV4";
    private static final String AWS_INSTANCE_PORT_ATTRIBUTE = "AWS_INSTANCE_PORT";

    private static final Random RAND = new Random(System.currentTimeMillis());

    @Value("${aws.cloudmap.namespace}")
    private String cloudMapNamespace;

    @Value("${aws.cloudmap.service}")
    private String cloudMapService;

    public CloudMapServiceLocationResolver() {
        log.info("ServiceLocationResolver: {}", this.getClass().getCanonicalName());
    }

    @Override
    public String resolve() {
        final AWSServiceDiscovery awsServiceDiscovery = AWSServiceDiscoveryClientBuilder.defaultClient();
        final DiscoverInstancesRequest discoverInstancesRequest = new DiscoverInstancesRequest();

        discoverInstancesRequest.setNamespaceName(cloudMapNamespace);
        discoverInstancesRequest.setServiceName(cloudMapService);
        discoverInstancesRequest.setHealthStatus(HealthStatus.HEALTHY.name());

        final DiscoverInstancesResult discoverInstancesResult = awsServiceDiscovery.discoverInstances(discoverInstancesRequest);

        final List<HttpInstanceSummary> allInstances = discoverInstancesResult.getInstances();

        if (log.isDebugEnabled()) {
            final List<String> serviceEndpoints = allInstances.stream().map(result -> result.getAttributes().get(AWS_INSTANCE_IPV_4_ATTRIBUTE) + ":" + result.getAttributes().get(AWS_INSTANCE_PORT_ATTRIBUTE)).collect(Collectors.toList());
            log.info("Found instances: {}", serviceEndpoints);
        }

        final HttpInstanceSummary result = allInstances.get(RAND.nextInt(allInstances.size()));
        final String serviceLocation = result.getAttributes().get(AWS_INSTANCE_IPV_4_ATTRIBUTE) + ":" + result.getAttributes().get(AWS_INSTANCE_PORT_ATTRIBUTE);

        log.info("Given {}, found {}", cloudMapService + "." + cloudMapNamespace, serviceLocation);

        return serviceLocation;
    }
}
