import com.example.dubbo.api.IDubboTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-client.xml");
        // 获得远程代理对象
        IDubboTest test = (IDubboTest)context.getBean("DubboTest");
        System.out.println(test.sayHello("杨大宇"));
    }

}
