package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * learn
 *
 * @author
 */
@Data
public class LearnAchieveDO extends LearnDO implements Serializable {
    private Date achieveTime;
    
    private SectionDO sectionDO;
    private CourseDO courseDO;
    private LearnProcessDO learnProcessDO;
    
}
