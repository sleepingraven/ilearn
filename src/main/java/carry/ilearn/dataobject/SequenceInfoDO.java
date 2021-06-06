package carry.ilearn.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * sequence_info
 * @author 
 */
@Data
public class SequenceInfoDO implements Serializable {
    private String name;

    private Integer currentValue;

    private Integer step;

    private static final long serialVersionUID = 1L;
}