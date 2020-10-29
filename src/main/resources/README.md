【springboot-es】
# springboot2x集成elasticsearch

## pom.xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.0.RELEASE</version>
	</parent>
	<dependencies>
		<!-- SpringBoot-整合Web组件 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		
		<!--ElasticSearch -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-elasticsearch</artifactId>
		</dependency>
	</dependencies>
	
## application.yml
es的健康检查地址一定要配置，否则每次启动都只能加载默认的 127.0.0.1:9200地址

	server:
	  port: 18085
	
	spring:
	  ## 默认为127.0.0.1:9200 ,修改es健康检查地址
	  elasticsearch:
	    rest:
	      uris: ["http://ip:9200"]
	  data:
	    elasticsearch:
	      repositories:
	        enabled: true
	      cluster-nodes: ip:9300
	      cluster-name: my-es
	      
## 核心代码
### Employee.java

	@Document(indexName = "springboot-es")
	public class Employee {
	    @Id
	    private String id;
	    @Field
	    private String firstName;
	    @Field
	    private String lastName;
	    @Field
	    private Integer age = 0;
	    @Field
	    private String about;	      
		
		//省掉get/set方法
	}

### EmployeeDao.java

	import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
	import com.maple.model.Employee;
	/**
	 * 
	 * @Description: 
	 * @author: uwank171 
	 * @date: 2020年10月28日 下午2:06:07 
	 *
	 */
	public interface EmployeeDao extends ElasticsearchRepository<Employee,String>{
	 
	    /**
	     * 查询雇员信息
	     * @param id
	     * @return
	     */
	    Employee queryEmployeeById(String id);
    
	}

### EmployeeController.java
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	import com.maple.dao.EmployeeDao;
	import com.maple.model.Employee;
	/**
	 * 
	 * @Description: 
	 * @author: uwank171 
	 * @date: 2020年10月28日 下午2:07:29 
	 *
	 */
	@RestController
	@RequestMapping("es")
	public class EmployeeController {
	    @Autowired
	    private EmployeeDao employeeDao;
	 
	    /**
	     * 添加
	     * @return
	     */
	    @GetMapping("/add")
	    public String add() {
	        Employee employee = new Employee();
	        employee.setId("1");
	        employee.setFirstName("xuxu");
	        employee.setLastName("zh");
	        employee.setAge(26);
	        employee.setAbout("i am in peking");
	        employeeDao.save(employee);
	        System.err.println("add a obj");
	        return "success";
	    }
	 
	    /**
	     * 删除
	     * @return
	     */
	    @GetMapping("delete")
	    public String delete() {
	        Employee employee = employeeDao.queryEmployeeById("1");
	        employeeDao.delete(employee);
	        System.err.println("delete a obj");
	        return "success";
	    }
	 
	    /**
	     * 局部更新
	     * @return
	     */
	    @GetMapping("update")
	    public String update() {
	        Employee employee = employeeDao.queryEmployeeById("1");
	        employee.setFirstName("哈哈");
	        employeeDao.save(employee);
	        System.err.println("update a obj");
	        return "success";
	    }
	    /**
	     * 查询
	     * @return
	     */
	    @GetMapping("query")
	    public Employee query() {
	        Employee accountInfo = employeeDao.queryEmployeeById("1");
	        System.err.println(accountInfo.toString());
	        return accountInfo;
	    }
	}

### EsApp.java
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	
	/**   
	 * @Description: 
	 * @author: uwank171 
	 * @date: 2020年10月28日 下午2:12:10 
	 *  
	 */
	@SpringBootApplication
	public class EsApp {
		public static void main(String[] args) {
			SpringApplication.run(EsApp.class, args);
		}
	}	

【错误提示】
1. 如果不加健康检查地址，启动就会出现警告信息
	Cannot create index: Timeout connecting to [localhost/127.0.0.1:9200]; nested exception is java.lang.RuntimeException: Timeout connecting to [localhost/127.0.0.1:9200]
	
2. 访问的时候会出现如下错误
	java.net.ConnectException: Timeout connecting to [localhost/127.0.0.1:9200]
	at org.apache.http.nio.pool.RouteSpecificPool.timeout(RouteSpecificPool.java:169) ~[httpcore-nio-4.4.13.jar:4.4.13]
	at org.apache.http.nio.pool.AbstractNIOConnPool.requestTimeout(AbstractNIOConnPool.java:632) ~[httpcore-nio-4.4.13.jar:4.4.13]
	//省略后面的错误...

3. application.yml为什么会出现警告错误红叉？
      cluster-nodes: ip:9300
      cluster-name: my-es
	是因为这两个属性已经弃用了，不过暂时不影响使用。


	
	
	
	
	
	
	
	
	
