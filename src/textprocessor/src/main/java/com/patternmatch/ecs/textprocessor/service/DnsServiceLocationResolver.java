package com.patternmatch.ecs.textprocessor.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.Type;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@ConditionalOnProperty(value = "servicediscovery.method", havingValue = "dns")
public class DnsServiceLocationResolver implements ServiceLocationResolver {

    private static final Random RAND = new Random(System.currentTimeMillis());

    @Value("${aws.cloudmap.namespace}")
    private String cloudMapNamespace;

    @Value("${aws.cloudmap.service}")
    private String cloudMapService;

    public DnsServiceLocationResolver() {
        log.info("ServiceLocationResolver: {}", this.getClass().getCanonicalName());
        org.xbill.DNS.ResolverConfig.getCurrentConfig();
    }

    private List<SRVRecord> lookup(String srvName) throws Exception {
        Record[] records;
        List<SRVRecord> srvRecords = new LinkedList<>();

        Lookup configLookup = new Lookup(srvName, Type.SRV);
        configLookup.setCache(null);
        configLookup.setResolver(new ExtendedResolver());
        records = configLookup.run();

        if (records != null && records.length > 0) {
            for (Record record : records) {
                SRVRecord srv = (SRVRecord) record;
                srvRecords.add(srv);
            }
        }

        return srvRecords;
    }

    @Override
    @SneakyThrows
    public String resolve() {
        String srvName = cloudMapService + "." + cloudMapNamespace;

        final List<SRVRecord> records = lookup(srvName);

        final SRVRecord record = records.get(RAND.nextInt(records.size()));

        final String result = record.getTarget().toString().replaceFirst("\\.$", "") + ":" + record.getPort();

        log.info("Given {}, found {}", srvName, result);

        return result;
    }
}
