package com.siemens.mindsphere.maa.controllertest;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.siemens.mindsphere.maa.providerservice.dao.PostgresRepository;
import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderServiceControllerTests extends AbstractJUnit4SpringContextTests{

	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private PostgresRepository postgresRepository;

	private static final String directory = "src/test/resources/";

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testHello() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/hello")
				.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Hello from provider")));

	}

	@Test
	public void testGetPgUser() throws Exception {
		JsonElement rawDataJson = new JsonParser().parse(
				Resources.toString(Resources.getResource("userlist.json"), Charsets.UTF_8));
		List<ProviderServiceDomainUser> postgresDataList = new Gson().fromJson(rawDataJson, new TypeToken<ArrayList<ProviderServiceDomainUser>>() {
		}.getType());
		for (ProviderServiceDomainUser data : postgresDataList) {
			postgresRepository.save(data);
		}

		mockMvc.perform(MockMvcRequestBuilders.get("/postgre/users/getUser")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new Gson().toJson(postgresDataList)))
				.andExpect(status().isOk())
				.andExpect(content().json(new Gson().toJson(postgresDataList)));

	}
	@Test
	public void testAddPgUser() throws Exception{

		long countBeforeAdd = postgresRepository.count();

		mockMvc.perform(MockMvcRequestBuilders.post("/postgre/users/addUser").contentType(MediaType.APPLICATION_JSON_VALUE).content
				(Resources
						.toString(Resources.getResource("user.json"), Charsets.UTF_8))).andExpect(status().isOk());

		long countAfterAdd = postgresRepository.count();
		Assert.assertTrue(countAfterAdd == (countBeforeAdd + 1));
	}

//	@Test
//	public void testgetMgUser() throws Exception {
//		JsonElement rawDataJson = new JsonParser().parse(
//				Resources.toString(Resources.getResource("userlist.json"), Charsets.UTF_8));
//		List<MongoDBUser> mongoDBUsers = new Gson().fromJson(rawDataJson, new TypeToken<ArrayList<MongoDBUser>>() {
//		}.getType());
//		for (MongoDBUser data : mongoDBUsers){
//			mongo.save(data);
//		}
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/mongo/users/getUser")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(new Gson().toJson(mongoDBUsers)))
//				.andExpect(status().isOk())
//				.andExpect(content().json(new Gson().toJson(mongoDBUsers)));
//
//	}
//
//	@Test
//	public void testAddMgUser() throws Exception {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("name"));
//		long countBeforeAdd = mongo.count(query, MongoDBUser.class);
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/mongo/users/addUser").contentType(MediaType.APPLICATION_JSON_VALUE).content
//				(Resources
//						.toString(Resources.getResource("user.json"), Charsets.UTF_8))).andExpect(status().isCreated());
//
//		long countAfterAdd = mongo.count(query, MongoDBUser.class);
//		Assert.assertTrue(countAfterAdd == (countBeforeAdd + 1));
//
//	}
//	@Test
//	public void testgetRdUser() throws Exception {
//		JsonElement rawDataJson = new JsonParser().parse(
//				Resources.toString(Resources.getResource("userlist.json"), Charsets.UTF_8));
//		RedisUser redisUser = new Gson().fromJson(rawDataJson, new TypeToken<ArrayList<RedisUser>>() {
//		}.getType());
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/mongo/users/getUser")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(new Gson().toJson(redisUser)))
//				.andExpect(status().isOk())
//				.andExpect(content().json(new Gson().toJson(redisUser)));
//
//	}
}