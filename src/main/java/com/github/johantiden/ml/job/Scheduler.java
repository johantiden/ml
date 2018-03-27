package com.github.johantiden.ml.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class Scheduler {

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    public static final int SECOND = 1_000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    private int videoLinkScannerPage;
    private int magnetUploadPage;


//    @Scheduled(fixedRate = 1000000000)
//    public void restartEvolver() {
//
//
//    }


}
