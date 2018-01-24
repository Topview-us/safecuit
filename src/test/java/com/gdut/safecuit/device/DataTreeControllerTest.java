package com.gdut.safecuit.device;

import com.gdut.safecuit.SafecuitApplication;
import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.DataTree;
import com.gdut.safecuit.device.common.vo.DataTreeVO;
import com.gdut.safecuit.device.web.controller.DataTreeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Garson in 20:35 2018/1/21
 * Description :
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@ContextConfiguration(classes = SafecuitApplication.class)//加载应用上下文
@WebAppConfiguration
public class DataTreeControllerTest {

	//private MockMvc mvc;
	private RestTemplate restTemplate = new RestTemplate();

	/*@Before
	public void setUp(){
		mvc = MockMvcBuilders.standaloneSetup(new DataTreeController()).build();
	}
*/
	/*@Test
	public void InsertGroupTest(){
		DataTree dataTree = new DataTree("分组一" ,3);
		//String result = restTemplate.postForObject("http://localhost:8080/dataTree/aaa" ,dataTree ,String.class);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/dataTree/aaa", dataTree, String.class);

		System.out.println(responseEntity.getBody());
	}

	@Test
	public void listGroupTest(){
		Integer typeId = 6;
		DataTreeVO[] dicResult = restTemplate.getForObject( "http://localhost:8080/dataTree/list?typeId={typeId}",
				DataTreeVO[].class, typeId);
		List<DataTreeVO> list = Arrays.asList(dicResult);
		System.out.println(list);
	}*/

}
