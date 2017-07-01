package de.tu_berlin.open_data.airquality.brandenburgairqualitydata.batch;

import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.*;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.http.HttpService;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.AirData;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.BMESensor;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.Schema;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.service.ApplicationService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.AbstractExcelItemReader;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.*;

import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by ahmadjawid on 5/21/17.
 */

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public ApplicationService applicationService;

    @Autowired
    @Qualifier("dataSource")
    public DataSource dataSource;



    private static final String PROPERTY_EXCEL_SOURCE_FILE_PATH = "excel.to.database.job.source.file.path";


    @Bean
    ItemReader<AirData> excelStudentReader(Environment environment) throws FileNotFoundException, MalformedURLException {
        AbstractExcelItemReader<AirData> reader = new PoiItemReader<>();



      //  URL url = new URL("https://luftdaten.brandenburg.de/home/-/bereich/datenexport/Montag.xls");
        //reader.setMaxItemCount(10);
        reader.setResource((new ClassPathResource("Montag.xls")));
        reader.setLinesToSkip(1);
       // reader.setMaxItemCount(10);


//        reader.setRowMapper(new BeanWrapperRowMapper<AirData>() {{
//            setTargetType(AirData.class);
//        }});

       reader.setRowMapper(excelRowMapper());
        return reader;
    }

//    private RowMapper<AirData> excelRowMapper() {
//        BeanWrapperRowMapper<AirData> rowMapper = new BeanWrapperRowMapper<>();
//        rowMapper.setTargetType(AirData.class);
//        return rowMapper;
//    }

    /**
     * If your Excel document has no header, you have to create a custom
     * row mapper and configure it here.
     */
    private RowMapper<AirData> excelRowMapper() {
       return new StudentExcelRowMapper();
    }

    @Bean
    ItemProcessor<AirData, AirData> excelStudentProcessor() {
        return new LoggingStudentProcessor();
    }

    @Bean
    ItemWriter<AirData> excelStudentWriter() {
        return new LoggingStudentWriter();
    }

    @Bean
    Step excelFileToDatabaseStep(ItemReader<AirData> excelStudentReader,
                                 ItemProcessor<AirData, AirData> excelStudentProcessor,
                                 ItemWriter<AirData> excelStudentWriter,
                                 StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("excelFileToDatabaseStep")
                .<AirData, AirData>chunk(1)
                .reader(excelStudentReader)
                .processor(excelStudentProcessor)
                .writer(excelStudentWriter)
                .build();
    }

    @Bean
    Job excelFileToDatabaseJob(JobBuilderFactory jobBuilderFactory,
                               @Qualifier("excelFileToDatabaseStep") Step excelStudentStep) {
        return jobBuilderFactory.get("excelFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .flow(excelStudentStep)
                .end()
                .build();
    }




    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcess(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }
}
