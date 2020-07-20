package com.dev.utils.reptile;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by 001977 on 2020-03-30 11:39.
 */
@Slf4j
public class GetChild extends BaseReptile {

	private static final Logger logger = LoggerFactory.getLogger(GetChild.class);

	private static final String INDEX_URL =
			"http://www.ltmic.com/index.php?m=data&industry=8321%2C8322%2C8323&region=4401&registerType=";

	public static void main(String[] args) {
		readUrl(INDEX_URL).ifPresent(GetChild::write);
	}


	public static Optional<List<Central>> readUrl(String url) {
		Response response = null;
		try {
			// 正式请求
			Request request = new Request.Builder()
					.url(url)
					.get()
					.addHeader("content-type", MediaType.APPLICATION_JSON_UTF8_VALUE)
					.addHeader("Cookie","PHPSESSID=f18f28747dc1396e3aea732a158bc4da; " +
							"UM_distinctid=1714e798c6f17a-07e288adfea11c8-4c302f7e-144000-1714e798c7012d; " +
							"CNZZDATA1253595096=1500675786-1586158045-http%253A%252F%252Fwww.ltmic.com%252F%7C1586158045; " +
							"QVrE_auth=7ef1LrF18tJc6zV7wng6-ELD9D7TWS2mM3dCZwDwYo0XW0mYmVXXCifyI49g7Ewtadmv1v" +
							"-Be2s_gS9CDXorYlKCsL5jsXbds7JGM3JM3oNjOZ98LHyIRmdRR0lGf-ZwA71esz5QbLuY_1bmxE63kux2q2D9rEY; " +
							"zQVrE__userid=f106i_Nq-QmndvRpxWIdFFpLELLQymERaygtgAOcqXcciw; " +
							"zQVrE__username=3edeay5DAIteTl0u_nYK4XyNnQIICbvrMmuMm4MlWZqchTP9SWpWOg; " +
							"zQVrE__groupid=0d6852ril2dr1KauLr5pSabT93Hh2L83WfXxgiEj; " +
							"zQVrE__nickname=2dbdOYYz8NBnfHIpxNDvu3Pj-nboYo-TKWTkNWYqbpbxkfpM4w")
					.build();
			response = InnerClass.CLIENT.newCall(request).execute();
			int i = 1;
			int code = response.code();
			while (code != HttpStatus.OK.value()) {
				if (code == HttpStatus.FORBIDDEN.value()) {
					logger.error("回执码={}该路径禁止访问!", code);
					break;
				}
				if (code == HttpStatus.NOT_FOUND.value()) {
					logger.error("回执码={}该路径资源不存在!", code);
					break;
				}
				response = InnerClass.CLIENT.newCall(request).execute();
				code = response.code();
				i++;
				if (i > 3) {
					break;
				}
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (code != HttpStatus.OK.value()) {
				logger.error("三次之后仍失败");
			}
			String content = new String(Objects.requireNonNull(response.body()).bytes(), StandardCharsets.UTF_8);
			Elements elements = Jsoup.parse(content).getElementsByClass("table-list");
			if (elements.size() > 0) {
				Element tablelist = elements.get(0);
				Element body = tablelist.child(0).child(1);
				List<Central> centralList = body.children().stream().map(c -> {
					Central central = new Central();
					central.setName(c.child(1).text());
					central.setProvince(c.child(3).text());
					central.setCity(c.child(4).text());
					central.setCcountry(c.child(5).text());
					central.setAddress(c.child(6).text());
					return central;
				}).collect(Collectors.toList());
				return Optional.of(centralList);
			}
		} catch (IOException e) {
			logger.error("此次业务失败，url->{},原因->{}", url, e);
		} finally {
			if (response != null) {
				//A connection to http://www.stats.gov.cn/ was leaked. Did you forget to close a response body?
				Objects.requireNonNull(response.body()).close();
				response.close();
			}
		}
		return Optional.empty();
	}

	public static void write(List<Central> location) {
		/*try {
			FileOutputStream out = new FileOutputStream("2019省市区-大陆ALL.json", true);
			out.write(JSON.toJSONString(location).getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		try {
			File file = new File("2020-诊所.data");
			List<String> data = location.stream().filter(Objects::nonNull).map(JSON::toJSONString).collect(Collectors.toList());
			FileUtils.writeLines(file, "UTF-8", data);
			logger.info("[{}]条文件写入成功！", data.size());
			data.clear();
		} catch (IOException e) {
			logger.error("文件写入异常，异常栈：", e);
		} finally {
			location.clear();
		}
	}


	@Data
	private static class Central {

		private String name;

		private String province;

		private String city;

		private String ccountry;

		private String address;
	}

}
