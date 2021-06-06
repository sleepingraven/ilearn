package carry.ilearn.controller;

import carry.ilearn.service.FileService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * @author Carry
 * @since 2021/5/15
 */
@RequestMapping("/download")
@RestController()
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private HttpServletRequest httpServletRequest;
    
    @GetMapping("/**")
    public void archive(HttpServletResponse response, @RequestParam(required = false) String originalName)
            throws Exception {
        String path = extractPath(httpServletRequest);
        File file = fileService.fetchFile(path);
        doFetch(response, file, originalName);
    }
    
    public static String extractPath(HttpServletRequest httpServletRequest) {
        final String path =
                httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        final String bestMatchingPattern =
                httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        
        return new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);
    }
    
    public void doFetch(HttpServletResponse response, File file, String originalName) throws Exception {
        response.reset();
        response.setContentType("application/octet-stream");
        //Content-Disposition 的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
        //attachment 表示以附件方式下载；inline 表示在线打开
        // filename 表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要解码才能获取到真正的名称
        final String contentDisposition = String.format("%s; filename=%s", "attachment", URLEncoder.encode(
                Optional.ofNullable(originalName).orElse(file.getName()), "UTF-8").replaceAll("\\+", " "));
        response.setHeader("Content-Disposition", contentDisposition);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        
        try (OutputStream os = response.getOutputStream(); InputStream is = new FileInputStream(file)) {
            FileCopyUtils.copy(is, os);
        }
    }
    
}
