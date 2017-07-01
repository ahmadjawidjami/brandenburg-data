package de.tu_berlin.open_data.airquality.brandenburgairqualitydata.batch;

import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.*;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.model.AirData;
import de.tu_berlin.open_data.airquality.brandenburgairqualitydata.service.ApplicationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.*;

import javax.sql.DataSource;
import java.io.*;
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
    ItemReader<AirData> excelStudentReader(Environment environment) throws IOException {
        AbstractExcelItemReader<AirData> reader = new MyItemReader();





      URL url = new URL("https://luftdaten.brandenburg.de/home/-/bereich/datenexport/Montag.xls");
        //reader.setMaxItemCount(10);
       // reader.setResource((new InputStreamResource(new PushbackInputStream(url.openStream()))));
        //reader.setResource((new ClassPathResource("Montag.xls")));
        reader.setResource(new InputStreamResource(new PushbackInputStream(new FileInputStream(new File("Montag-work.xls")))));
        reader.setLinesToSkip(5);

       // reader.setMaxItemCount(20);
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
       return new AirDataExcelRowMapper();
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
