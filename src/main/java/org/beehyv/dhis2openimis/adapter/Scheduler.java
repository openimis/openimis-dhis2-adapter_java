package org.beehyv.dhis2openimis.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.beehyv.dhis2openimis.adapter.util.ParamsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.beans.factory.InitializingBean;

@Configuration
@EnableScheduling
public class Scheduler implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
	@Autowired private Tester adapterCycle;

	@Value("${app.schedule.initFrom}")
	String initDateString;

    private void init() throws Exception{
		try
		{
			LocalDate initDate  = LocalDate.parse(initDateString);
			ParamsUtil.REF_DATE_PARAM = "refDate=" + initDate.toString();
			logger.info("Program cycle started at: " + LocalDateTime.now().toString());
			adapterCycle.run();
			logger.info("Program cycle finished at: " + LocalDateTime.now().toString());

		}catch (Exception e) {
			throw e;
		}
	}

	@Override
    public void afterPropertiesSet() throws Exception {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(false);
		try {
			sdf.parse(initDateString);
			init();
		} catch (Exception e) {
			throw e;
		}
    }

	@Scheduled(cron = "${app.schedule.cronExpression}")
	public void runAdapter() {
		ParamsUtil.REF_DATE_PARAM = "refDate=" + LocalDate.now().minusDays(1).toString();
		logger.info("Program cycle started at: " + LocalDateTime.now().toString());
		adapterCycle.run();
		logger.info("Program cycle finished at: " + LocalDateTime.now().toString());
	}
}
