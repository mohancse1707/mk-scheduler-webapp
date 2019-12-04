package com.mohan.springboot.app;

import com.mohan.springboot.app.config.scheduler.JobScheduleCreator;
import com.mohan.springboot.app.repository.scheduler.CustomSchedulerRepository;
import com.mohan.springboot.app.repository.scheduler.SchedulerRepository;
import com.mohan.springboot.app.repository.scheduler.impl.SchedulerRepositoryImpl;
import com.mohan.springboot.app.service.dto.JobSchedulerDetailsDTO;
import com.mohan.springboot.app.service.scheduler.SchedulerService;
import com.mohan.springboot.app.service.scheduler.impl.SchedulerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchedulerApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class SchedulerServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
    private SchedulerService schedulerService;

    @TestConfiguration
    static class SchedulerServiceTestContextConfiguration {
		@Bean
		public SchedulerService schedulerService() {
			return new SchedulerServiceImpl();
		}
		@Bean
        public SchedulerFactoryBean schedulerFactoryBean() {
		    return new SchedulerFactoryBean();
        }
        @Bean
        public CustomSchedulerRepository schedulerRepository() {
		    return new SchedulerRepositoryImpl();
        }
        @Bean
        public JobScheduleCreator scheduleCreator() {
		    return new JobScheduleCreator();
        }
    }

    @Test
    @Rollback(false)
    public void fetchAllJobSchedulersTest() {

    }

    @Test
    @Rollback(false)
    public void scheduleNewJobTest() {
        JobSchedulerDetailsDTO jobSchedulerDetailsDTO = new JobSchedulerDetailsDTO();
        jobSchedulerDetailsDTO.setFrequency("0 * * ? * *");
        jobSchedulerDetailsDTO.setJobClass("com.mohan.springboot.app.jobs.SimpleJob");
        jobSchedulerDetailsDTO.setJobName("SIMPLEJOB");
        jobSchedulerDetailsDTO.setJobGroup("SIMPLEJOBGROUP");
        jobSchedulerDetailsDTO.setPriority(5l);
        jobSchedulerDetailsDTO.setJobStatus("CREATED");
        jobSchedulerDetailsDTO.setStartDate(new Date());
        boolean resultOne = schedulerService.scheduleNewJob(jobSchedulerDetailsDTO);
        Assert.assertTrue(resultOne);

        JobSchedulerDetailsDTO jobSchedulerDetailsDTO2 = new JobSchedulerDetailsDTO();
        jobSchedulerDetailsDTO2.setFrequency("0 */2 * ? * *");
        jobSchedulerDetailsDTO2.setJobClass("com.mohan.springboot.app.jobs.SimpleJob");
        jobSchedulerDetailsDTO2.setJobName("SIMPLEJOB2");
        jobSchedulerDetailsDTO2.setJobGroup("SIMPLEJOBGROUP");
        jobSchedulerDetailsDTO2.setPriority(5l);
        jobSchedulerDetailsDTO2.setJobStatus("CREATED");
        jobSchedulerDetailsDTO2.setStartDate(new Date());
        boolean resultTwo = schedulerService.scheduleNewJob(jobSchedulerDetailsDTO2);
        Assert.assertTrue(resultTwo);

        JobSchedulerDetailsDTO jobSchedulerDetailsDTO3 = new JobSchedulerDetailsDTO();
        jobSchedulerDetailsDTO2.setFrequency("0 */2 * ? * *");
        jobSchedulerDetailsDTO2.setJobClass("com.mohan.springboot.app.jobs.SimpleJob");
        jobSchedulerDetailsDTO2.setJobName("SIMPLEJOB3");
        jobSchedulerDetailsDTO2.setJobGroup("SIMPLEJOBGROUP");
        jobSchedulerDetailsDTO2.setPriority(5l);
        jobSchedulerDetailsDTO2.setJobStatus("CREATED");
        jobSchedulerDetailsDTO2.setStartDate(new Date());
        boolean resultThree = schedulerService.scheduleNewJob(jobSchedulerDetailsDTO2);
        Assert.assertTrue(resultThree);
    }

    @Test
    @Rollback(false)
    public void updateScheduleJobTest() {
        JobSchedulerDetailsDTO jobSchedulerDetailsDTO = new JobSchedulerDetailsDTO();
        jobSchedulerDetailsDTO.setId(2l);
        jobSchedulerDetailsDTO.setFrequency("0 * * ? * *");
        jobSchedulerDetailsDTO.setJobClass("com.mohan.springboot.app.jobs.SimpleJob");
        jobSchedulerDetailsDTO.setJobName("SIMPLEJOB");
        jobSchedulerDetailsDTO.setJobGroup("SIMPLEJOBGROUP");
        jobSchedulerDetailsDTO.setPriority(5l);
        jobSchedulerDetailsDTO.setJobStatus("CHANGED");
        jobSchedulerDetailsDTO.setStartDate(new Date());
        boolean result = schedulerService.updateScheduleJob(jobSchedulerDetailsDTO);
    }

    @Test
    @Rollback(false)
    public void deleteJobTest() {
        JobSchedulerDetailsDTO jobSchedulerDetailsDTO = new JobSchedulerDetailsDTO();
        jobSchedulerDetailsDTO.setId(3l);
        jobSchedulerDetailsDTO.setFrequency("0 * * ? * *");
        jobSchedulerDetailsDTO.setJobClass("com.mohan.springboot.app.jobs.SimpleJob");
        jobSchedulerDetailsDTO.setJobName("SIMPLEJOB");
        jobSchedulerDetailsDTO.setJobGroup("SIMPLEJOBGROUP");
        jobSchedulerDetailsDTO.setPriority(5l);
        jobSchedulerDetailsDTO.setJobStatus("CANCELLED");
        jobSchedulerDetailsDTO.setStartDate(new Date());
        boolean result = schedulerService.deleteJob(jobSchedulerDetailsDTO);
    }

}
