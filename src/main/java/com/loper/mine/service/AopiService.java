package com.loper.mine.service;

import com.loper.mine.mapper.mappers.PmsTestMapper;
import com.loper.mine.mapper.repositry.AopiRepositry;
import com.loper.mine.model.Aopi;
import com.loper.mine.model.PmsTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zgq7 on 2019/7/9.
 */

@Service(AopiService.PACKAGE_BEAN_NAME)
@Transactional(rollbackFor = Exception.class)
public class AopiService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String PACKAGE_BEAN_NAME = "aopiService";

	@Resource(name = AopiRepositry.PACKAGE_BEAN_NAME)
	private AopiRepositry aopiRepositry;
	@Resource
	private PmsTestMapper pmsTestMapper;

	public List<Aopi> getAopList() {
		return aopiRepositry.selectAll();
	}

	public void insertPmsList() {
		PmsTest test1 = new PmsTest(1, "1");
		PmsTest test2 = new PmsTest(2, null);
		PmsTest test3 = new PmsTest(3, "1");
		List<PmsTest> pmsTestList = Arrays.asList(test1, test2, test3);
		pmsTestMapper.insertListUseDefinedId(pmsTestList);

		try {
			TimeUnit.MINUTES.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
