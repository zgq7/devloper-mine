package com.dev.utils.zpang;

import com.alibaba.fastjson.JSON;
import com.dev.config.LocalThreadPool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by 001977 on 2020-03-30 11:39.
 */
@Slf4j
public class GetProvince {

	//省节点
	public static final String PROVINCE_ELEMENT = "provincetr";
	//市节点
	public static final String CITY_ELEMENT = "citytr";
	//县/区节点
	public static final String COUNTRY_ELEMENT = "countytr";
	//镇/街道/乡节点
	public static final String TOWN_ELEMENT = "towntr";
	//村/社区节点
	public static final String VILLAGE_ELEMENT = "villagetr";
	// 正则
	public static final Pattern PATTERN = Pattern.compile("[0-9]+");
	private static final Logger logger = LoggerFactory.getLogger(GetProvince.class);
	//基础路径
	private static final String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html";


	public static void main(String[] args) {
		final ThreadPoolExecutor executor = LocalThreadPool.getInstance().threadPoolExecutor;
		LocalDateTime start = LocalDateTime.now();
		CountDownLatch countDownLatch = new CountDownLatch(31);
		// 共31个 省/直辖市
		List<Location> data = new ArrayList<>(50000);
		// 拿到所有的省，并遍历
		readUrl(baseUrl, PROVINCE_ELEMENT).ifPresent(privinces -> privinces.forEach(p -> {
			//省页面 每个 tr 有多个省,每个省的数据单独用一个线程去跑
			p.children().stream().filter(child -> !child.select("a").isEmpty()).forEach(child ->
					{
						executor.execute(() -> {
							List<Location> locations = new ArrayList<>();
							Location province = parseLocation(child, 0L, 1, true);
							//设置线程名称
							final String originName = Thread.currentThread().getName();
							Thread.currentThread().setName(province.getName());

							locations.add(province);
							String cityUrl = getHrefUrl(baseUrl, child);
							readUrl(cityUrl, CITY_ELEMENT).ifPresent(cities -> cities.forEach(c -> {
								Location city = parseLocation(c, Objects.requireNonNull(province).getCode(), 2, false);
								locations.add(city);

								String countryUrl = getHrefUrl(cityUrl, c);
								readUrl(countryUrl, COUNTRY_ELEMENT).ifPresent(counties -> counties.forEach(x -> {
									Location country = parseLocation(x, Objects.requireNonNull(city).getCode(), 3, false);
									locations.add(country);
									String townUrl = getHrefUrl(countryUrl, x);
									readUrl(townUrl, TOWN_ELEMENT).ifPresent(towns -> towns.forEach(t -> {
										Location town = parseLocation(t, Objects.requireNonNull(country).getCode(), 4, false);
										data.add(town);

/*										String villageUrl = getHrefUrl(townUrl, t);
										readUrl(villageUrl, VILLAGE_ELEMENT).ifPresent(villages -> villages.forEach(v -> {
											Location village = parseLocation(v, Objects.requireNonNull(town).getCode(), 5, false);
											data.add(village);
										}));*/

									}));
								}));
							}));
							data.addAll(locations);
							countDownLatch.countDown();
							logger.info("[{}]已爬取完成,剩余个{}省/直辖市", Thread.currentThread().getName(), countDownLatch.getCount());
							Thread.currentThread().setName(originName);
						});
					}
			);
		}));

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			logger.error("闭锁报错：", e);
		}

		//线程池不会主动释放核心线程以保证下次请求进来时不在初始化核心线程
		//手动关闭线程池
		executor.shutdown();

		logger.info("共花费 {} 分钟，{}条记录即将写入文件...", LocalDateTime.now().getMinute() - start.getMinute(), data.size());
		write(data);
		//手动关闭okhttp connection
		InnerClass.shutdownOkHttp();
	}

	/**
	 * 获取首字母
	 *
	 * @param chinese
	 * @return
	 */
	private static String getFirstSpell(String chinese) {
		try {
			HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
			defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
			String[] temp = new String[0];
			try {
				temp = PinyinHelper.toHanyuPinyinStringArray(chinese.charAt(0), defaultFormat);
			} catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
				badHanyuPinyinOutputFormatCombination.printStackTrace();
			}
			return temp[0].charAt(0) + "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将elemnt 转化为 Location 对象
	 *
	 * @param element
	 * @param level     Location 的等级
	 * @param isProvice 是否需要校验 partner
	 **/
	public static Location parseLocation(Element element, Long parentCode, Integer level, boolean isProvice) {
		String name;
		String code;

		if (isProvice) {
			int end = element.select("a").attr("href").indexOf(".");
			code = element.select("a").attr("href").substring(0, end);
			name = element.text();
		} else {
			code = element.child(0).text();
			if (level != 5) {
				name = element.child(1).text();
			} else {
				name = element.child(2).text();
			}
		}

		logger.info("地名：{}  代码：{}  级别：{}", name, code, level);

		// 设置区信息
		Location county = new Location();
		county.setCode(Long.valueOf(code));
		county.setName(name);
		county.setLevel(level);
		county.setLetterSort(getFirstSpell(name));
		county.setParentCode(parentCode);
		return county;
	}

	public static String getHrefUrl(String parentUrl, Element element) {
		String path = "";
		if (parentUrl != null) {
			if (parentUrl.contains("/")) {
				int end = parentUrl.lastIndexOf("/") + 1;
				path = parentUrl.substring(0, end);
			}
		}
		return path + element.select("a").attr("href");
	}

	/**
	 * 获取该 url 的指定的所有的 节点
	 *
	 * @param url
	 * @param elementName 需要的节点
	 * @return
	 * @throws IOException
	 */
	public static Optional<Elements> readUrl(final String url, final String elementName) {
		Response response = null;
		try {
			Request request = new Request.Builder()
					.url(url)
					.get()
					.header("content-type", MediaType.APPLICATION_JSON_UTF8_VALUE)
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
				logger.error("执行请求异常，结果回执码：{}，节点级别：{}，url = {},总共请求{}次", code, elementName, url, i);
				String location = response.header("Location");
			}
			String content = new String(Objects.requireNonNull(response.body()).bytes(), "gb2312");
			return Optional.ofNullable(Jsoup.parse(content).getElementsByClass(elementName));
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

	/**
	 * 文件写入 增量
	 * 使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
	 *
	 * @param location 将被写入文件的对象
	 **/
	public static void write(List<Location> location) {
		/*try {
			FileOutputStream out = new FileOutputStream("2019省市区-大陆ALL.json", true);
			out.write(JSON.toJSONString(location).getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		try {
			File file = new File("2019省市区-大陆ALL.data");
			List<String> data = location.stream().filter(Objects::nonNull).map(JSON::toJSONString).collect(Collectors.toList());
			FileUtils.writeLines(file, "UTF-8", data);
			logger.info("[{}]条文件写入成功！", data.size());
			data.clear();
		} catch (IOException e) {
			logger.error("文件写入异常，异常栈：", e);
		} finally {
			long provinceCount = location.stream().filter(o -> o.getLevel().equals(1)).count();
			long cityCount = location.stream().filter(o -> o.getLevel().equals(2)).count();
			long countryCount = location.stream().filter(o -> o.getLevel().equals(3)).count();
			long townCount = location.stream().filter(o -> o.getLevel().equals(4)).count();
			long villageCount = location.stream().filter(o -> o.getLevel().equals(5)).count();
			logger.info("省：{} 市：{} 县：{} 镇：{} 村：{}", provinceCount, cityCount, countryCount, townCount, villageCount);
			location.clear();
		}
	}

	@Data
	private static class Location {
		private Long parentCode;
		private Long code;
		private String name;
		private Integer level;
		private String letterSort;
		private List<Location> childs;
	}

	/**
	 * 内部类
	 **/
	private static class InnerClass {

		/**
		 * OkHttp3 客户端
		 **/
		public static final OkHttpClient CLIENT = new OkHttpClient.Builder()
				// java.net.ProtocolException: Too many follow-up requests: 21
				.followSslRedirects(false)
				.followRedirects(false)
				// 连接池配置
				.connectTimeout(5, TimeUnit.MINUTES)
				.writeTimeout(5, TimeUnit.MINUTES)
				.readTimeout(5, TimeUnit.MINUTES)
				.connectionPool(new ConnectionPool(0, 30, TimeUnit.MINUTES))
				.build();

		/**
		 * 关闭连接池中所有的连接
		 **/
		public static void shutdownOkHttp() {
			CLIENT.connectionPool().evictAll();
		}

	}


}
