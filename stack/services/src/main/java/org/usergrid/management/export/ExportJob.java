package org.usergrid.management.export;


import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.usergrid.batch.JobExecution;
import org.usergrid.batch.job.OnlyOnceJob;
import org.usergrid.management.ExportInfo;
import org.usergrid.persistence.entities.JobData;


/**
 *
 *
 */
@Component("exportJob")
public class ExportJob extends OnlyOnceJob {
    private static final Logger logger = LoggerFactory.getLogger( ExportJob.class );

    ExportService exportService;

    public ExportJob() {
        logger.info("ExportJob created " + this);
    }

    @Override
    public void doJob(JobExecution jobExecution) throws Exception {
        logger.info( "execute ExportJob {}", jobExecution );

        JobData jobData = jobExecution.getJobData();
        UUID exportId = ( UUID ) jobData.getProperty("exportId");
        //this is probably the state info that todd mentioned
        ExportInfo config = (ExportInfo) jobData.getProperty( "exportInfo" );

        exportService.doExport( config );

        logger.info( "executed ExportJob completed normally" );
    }

    @Override
    protected long getDelay(JobExecution jobExecution) throws Exception {
        //return arbitrary number
        return 100;
    }
}
