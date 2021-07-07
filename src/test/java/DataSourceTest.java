import com.maruko.Application;
import com.maruko.entity.JdbcEntity;
import com.maruko.entity.ParamsEntity;
import com.maruko.entity.SqlEntity;
import com.maruko.service.ParamsService;
import com.maruko.service.SqlService;
import com.maruko.vo.ResponseVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.maruko.service.JdbcService;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试用例〉
 *
 * @author maruko
 * @date 2021/7/7 10:19
 * @since 1.0.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DataSourceTest {

    @Autowired
    JdbcService jdbcService;

    @Autowired
    SqlService sqlService;

    @Autowired
    ParamsService paramsService;

    @Test
    public void jdbcAddTest() {
        jdbcService.addJdbc(null, new JdbcEntity("MySQL", "jdbc:mysql://localhost:3307/courttax?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&allowMultiQueries=true&useSSL=false",
                "root", "123456"));
    }

    @Test
    public void sqlAddTest() {
        sqlService.addSql(null, new SqlEntity(1L, "select * from test where age=?"));
    }

    @Test
    public void paramsAddTest() {
        paramsService.addParams(null, new ParamsEntity(1l, null, 18, null));
    }

    @Test
    public void executeSqlTest() {
        ResponseVo responseVo = sqlService.executeSql(null, 1L);
        int code = responseVo.getCode();
        System.out.println(code);
        Object data = responseVo.getData();
        System.out.println(data);
    }
}
