package carry.ilearn.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Carry
 * @since 2021/5/18
 */
@NoArgsConstructor
@Data
public class FileSaveQuery {
    MultipartFile file;
    String category;
    
    String pathPrefix;
    
    public FileSaveQuery(MultipartFile file, String category) {
        this.file = file;
        this.category = category;
    }
    
    public FileSaveQuery(MultipartFile file, String category, String pathPrefix) {
        this.file = file;
        this.category = category;
        this.pathPrefix = pathPrefix;
    }
    
}
