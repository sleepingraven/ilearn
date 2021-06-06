package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Data
public class FileCreateQuery {
    String extension;
    String category;
    
    String pathPrefix;
    
}
