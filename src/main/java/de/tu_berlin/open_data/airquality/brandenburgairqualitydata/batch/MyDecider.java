package de.tu_berlin.open_data.airquality.brandenburgairqualitydata.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * Created by ahmadjawid on 7/1/17.
 */
public class MyDecider implements JobExecutionDecider {
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
//        if () {
//            return "FAILED";
//        }
//        else {
//            return "COMPLETED";
//        }

        return null;
    }
}
