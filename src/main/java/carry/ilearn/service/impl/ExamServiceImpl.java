package carry.ilearn.service.impl;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.error.EmBusinessError;
import carry.ilearn.converter.ExamConverter;
import carry.ilearn.dao.ExamDao;
import carry.ilearn.dao.UserExamDao;
import carry.ilearn.dataobject.ExamDO;
import carry.ilearn.dataobject.UserExamDO;
import carry.ilearn.query.ExamAddQuery;
import carry.ilearn.query.ExamJoinQuery;
import carry.ilearn.query.ExamPageQuery;
import carry.ilearn.service.ExamService;
import carry.ilearn.service.datatransferobject.ExamDTO;
import carry.ilearn.service.utils.ExamServiceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ExamServiceImpl implements ExamService {
    @Resource
    private ExamDao examDao;
    @Resource
    private ExamConverter examConverter;
    @Resource
    private UserExamDao userExamDao;
    
    @Override
    public String addExam(ExamAddQuery examAddQuery) {
        final ExamDO examDO = new ExamDO();
        examDO.setTitle(examAddQuery.getTitle());
        examDO.setStartTime(examAddQuery.getStartTime());
        examDO.setTotalTime(examAddQuery.getTotalTime());
        String href = examAddQuery.getHref();
        if (href != null) {
            href = href.trim();
            if (href.length() > 0 && Character.isLetter(href.charAt(0))) {
                if (href.length() <= 4 || !"http".equalsIgnoreCase(href.substring(0, 4))) {
                    href = "http://" + href;
                }
            }
            examDO.setHref(href);
        }
        examDO.setUserId(examAddQuery.getUserId());
        examDao.insertSelective(examDO);
        
        String code = ExamServiceUtil.base34(examDO.getId());
        examDO.setCode(code);
        examDao.updateByPrimaryKeySelective(examDO);
        return code;
    }
    
    @Override
    public List<ExamDTO> getExamPaged(ExamPageQuery examPageQuery) {
        final List<ExamDO> examDOS = examDao.selectByUserId(examPageQuery.getUserId());
        final List<ExamDTO> examDTOS = examConverter.listExamDO2ExamDTO(examDOS);
        return examDTOS;
    }
    
    @Override
    public ExamDTO getExamByCode(String code) {
        final ExamDO examDO = examDao.selectByCode(code);
        return examConverter.examDO2ExamDTO(examDO);
    }
    
    @Override
    public void doJoinExam(ExamJoinQuery examJoinQuery) throws BusinessException {
        final ExamDO examDO = examDao.selectByPrimaryKey(examJoinQuery.getExamId());
        if (examDO == null) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "考试不存在");
        }
        final Date date = new Date();
        final Date startTime = examDO.getStartTime();
        final Date endTime = new Date(startTime.getTime() + examDO.getTotalTime() * 60 * 1000);
        if (endTime.before(date)) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "考试已经结束");
        }
        
        final UserExamDO userExamDO = new UserExamDO();
        userExamDO.setExamId(examJoinQuery.getExamId());
        userExamDO.setUserId(examJoinQuery.getUserId());
        try {
            userExamDao.insertSelective(userExamDO);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "不能重复加入");
        }
    }
    
    @Override
    public List<ExamDTO> getExamJoint(Integer userId) throws BusinessException {
        final List<ExamDO> examDOS = examDao.selectJointByUserId(userId);
        return examConverter.listExamDO2ExamDTO(examDOS);
    }
    
}
