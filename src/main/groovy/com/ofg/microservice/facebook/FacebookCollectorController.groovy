package com.ofg.microservice.facebook

import groovy.util.logging.Slf4j
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.GET

@Slf4j
@RestController
@RequestMapping('/api')
class FacebookCollectorController {

    private final FacebookCollector collector

    @Autowired
    FacebookCollectorController(FacebookCollector collector) {
        this.collector = collector
    }

    @ResponseStatus(OK)
    @RequestMapping(method = GET, value = '/{facebookId}/{pairId}', produces = 'application/json')
    Callable<Void> getPlacesFromTweets(@PathVariable @NotNull String facebookId, @PathVariable @NotNull long pairId) {
        collector.collectAndPassToAnalyzers(facebookId, pairId)
        return {
            log.info("Facebook collector called for facebookId: ${facebookId} and pairId: ${pairId}")
        } as Callable<Void>
    }

}
