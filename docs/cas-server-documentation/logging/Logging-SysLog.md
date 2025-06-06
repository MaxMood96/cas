---
layout: default
title: CAS - SysLog Logging Configuration
category: Logs & Audits
---

{% include variables.html %}

# SysLog Logging

CAS logging framework does have the ability to route messages to an external
syslog instance. To configure this, you first configure the `SysLogAppender` and then specify which
messages need to be routed over to this instance:

```xml
...
<Appenders>
    <Syslog name="SYSLOG" format="RFC5424" host="localhost" port="8514"
            protocol="TCP" appName="MyApp" includeMDC="true" mdcId="mdc"
            facility="LOCAL0" enterpriseNumber="18060" newLine="true"
            messageId="Audit" id="App"/>
</Appenders>
...
<Logger name="org.apereo" additivity="true" level="debug">
    <appender-ref ref="cas" />
    <appender-ref ref="SYSLOG" />
</Logger>

```

<div class="alert alert-warning">:warning: <strong>Pay Attention</strong><br /><a href="Logging-MDC.html">Mapped Diagnostic Context</a> (<code>MDC</code>) may contain the 
password. Setting <code>includeMDC=true</code> sends clear password as a variable to SysLog.
</div>

You can also configure the remote destination output over
SSL and specify the related keystore configuration:

```xml
...

<Appenders>
    <TLSSyslog name="bsd" host="localhost" port="6514">
      <SSL>
        <KeyStore location="log4j2-keystore.jks" password="changeme"/>
        <TrustStore location="truststore.jks" password="changeme"/>
      </SSL>
    </TLSSyslog>
</Appenders>

...
```
