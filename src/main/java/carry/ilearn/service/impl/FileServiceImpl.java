package carry.ilearn.service.impl;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.error.EmBusinessError;
import carry.ilearn.dao.SequenceInfoDao;
import carry.ilearn.dataobject.SequenceInfoDO;
import carry.ilearn.query.FileCreateQuery;
import carry.ilearn.query.FileSaveQuery;
import carry.ilearn.service.FileService;
import carry.ilearn.service.datatransferobject.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Carry
 * @since 2021/5/16
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService, InitializingBean {
    @Value("${app.filesystem.server}")
    private String server;
    @Value("${app.filesystem.resourceLocation}")
    private String resourceLocation;
    @Value("${app.filesystem.initCategories}")
    private String[] initCategories;
    @Value("${app.filesystem.pathPrefix}")
    private String pathPrefix;
    @Resource
    private SequenceInfoDao sequenceInfoDao;
    
    @Override
    public void afterPropertiesSet() {
        Arrays.stream(initCategories)
              .map(category -> resourceLocation + category)
              .forEach(FileServiceImpl::ensureDirectoryExists);
        log.info("已初始化资源目录：" + Arrays.toString(initCategories));
    }
    
    @Override
    public FileDTO saveFile(FileSaveQuery fileSaveQuery) throws IOException {
        String name = getUniqueSequence();
        String originalFilename = fileSaveQuery.getFile().getOriginalFilename();
        int lastDot = originalFilename.lastIndexOf(".");
        if (lastDot > 0) {
            name += originalFilename.substring(lastDot);
        }
        name = fileSaveQuery.getCategory() + "/" + name;
        
        File destFile = new File(resourceLocation + name);
        fileSaveQuery.getFile().transferTo(destFile);
        
        final FileDTO fileDTO = new FileDTO();
        fileDTO.setFile(destFile);
        fileDTO.setUrl(
                String.format("%s%s/%s", server, Optional.ofNullable(fileSaveQuery.getPathPrefix()).orElse(pathPrefix),
                              name));
        return fileDTO;
    }
    
    @Override
    public FileDTO createFile(FileCreateQuery fileCreateQuery) {
        String name = getUniqueSequence();
        if (StringUtils.isNotBlank(fileCreateQuery.getExtension())) {
            name += "." + fileCreateQuery.getExtension();
        }
        name = fileCreateQuery.getCategory() + "/" + name;
        
        File destFile = new File(resourceLocation + name);
        
        final FileDTO fileDTO = new FileDTO();
        fileDTO.setFile(destFile);
        fileDTO.setUrl(String.format("%s%s/%s", server,
                                     Optional.ofNullable(fileCreateQuery.getPathPrefix()).orElse(pathPrefix), name));
        return fileDTO;
    }
    
    @Override
    public File fetchFile(String path) throws Exception {
        File file = new File(resourceLocation + path);
        if (!file.isFile()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "文件不存在");
        }
        return file;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public String getUniqueSequence() {
        SequenceInfoDO sequenceDO = sequenceInfoDao.getSequenceByName("application_filesystem");
        int sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequence + sequenceDO.getStep());
        sequenceInfoDao.updateByPrimaryKeySelective(sequenceDO);
        return String.format("%010d", sequence);
    }
    
    private static void ensureDirectoryExists(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
    }
    
}
