package carry.ilearn.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Carry
 * @since 2021/5/16
 */
@Slf4j
@Configuration
public class FileConfig implements WebMvcConfigurer, InitializingBean {
    @Value("${app.filesystem.resourceLocation}")
    private String resourceLocation;
    @Value("${app.filesystem.pathPrefix}")
    private String pathPrefix;
    
    @Override
    public void afterPropertiesSet() {
        log.info("已连接到资源路径：" + resourceLocation);
    }
    
    /**
     * 上传的图片在F盘下的file目录下，访问路径如：http://localhost:8080/file/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
     * 其中file表示访问的前缀。"file:F:/file/"是文件真实的存储路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(String.format("/%s/**", pathPrefix))
                .addResourceLocations(String.format("file:%s", resourceLocation));
    }
    
}
