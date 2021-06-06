package carry.ilearn.service;

import carry.ilearn.query.FileCreateQuery;
import carry.ilearn.query.FileSaveQuery;
import carry.ilearn.service.datatransferobject.FileDTO;

import java.io.File;
import java.io.IOException;

/**
 * @author Carry
 * @since 2021/5/16
 */
public interface FileService {
    
    FileDTO saveFile(FileSaveQuery fileSaveQuery) throws IOException;
    
    FileDTO createFile(FileCreateQuery fileCreateQuery);
    
    File fetchFile(String path) throws Exception;
    
}
