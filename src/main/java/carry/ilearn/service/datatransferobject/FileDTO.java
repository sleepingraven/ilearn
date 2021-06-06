package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.io.File;

/**
 * @author Carry
 * @since 2021/5/20
 */
@Data
public class FileDTO {
    private File file;
    private String url;
    
}
