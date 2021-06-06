package carry.ilearn.converter;

import carry.ilearn.controller.viewobject.CommentVO;
import carry.ilearn.controller.viewobject.PostVO;
import carry.ilearn.controller.viewobject.StatementVO;
import carry.ilearn.controller.viewobject.UserReceivedRepliesVO;
import carry.ilearn.dataobject.*;
import carry.ilearn.service.datatransferobject.CommentDTO;
import carry.ilearn.service.datatransferobject.PostDTO;
import carry.ilearn.service.datatransferobject.StatementDTO;
import carry.ilearn.service.datatransferobject.UserReceivedRepliesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Mapper(componentModel = "spring")
public interface DiscussionConverter {
    
    StatementDTO statementDO2StatementDTO(StatementDO statementDO);
    
    @Mapping(source = "statementDO", target = "statementDTO")
    PostDTO postDO2PostDTO(PostDO postDO);
    
    List<PostDTO> listPostDO2PostDTO(List<PostDO> postDOList);
    
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.avatarUrl", target = "avatarUrl")
    StatementVO statementDTO2StatementVO(StatementDTO statementDTO);
    
    @Mapping(source = "statementDTO", target = "statement")
    PostVO postDTO2PostVO(PostDTO postDTO);
    
    List<PostVO> listPostDTO2PostVO(List<PostDTO> postDTOList);
    
    @Mapping(source = "statementDO", target = "statementDTO")
    CommentDTO commentDO2CommentDTO(CommentDO commentDO);
    
    List<CommentDTO> listCommentDO2CommentDTO(List<CommentDO> commentDOList);
    
    @Mapping(source = "statementDO", target = "statementDTO")
    @Mapping(source = "commentDOList", target = "commentDTOList")
    CommentDTO commentDO2CommentDTO(CommentFirstLevelDO commentDO);
    
    List<CommentDTO> listFirstLeveCommentDO2CommentDTO(List<CommentFirstLevelDO> commentDOList);
    
    @Mapping(source = "statementDTO", target = "statement")
    @Mapping(source = "commentDTOList", target = "comments")
    CommentVO commentDTO2CommentVO(CommentDTO commentDTO);
    
    List<CommentVO> listCommentDTO2CommentVO(List<CommentDTO> commentDTOList);
    
    @Mapping(source = "postDO.title", target = "postTitle")
    @Mapping(source = "userFromDO.id", target = "userFromId")
    @Mapping(source = "userFromDO.name", target = "userFromName")
    UserReceivedRepliesDTO userReceivedRepliesDO2UserReceivedRepliesDTO(UserReceivedRepliesDO userReceivedRepliesDO);
    
    List<UserReceivedRepliesDTO> listUserReceivedRepliesDO2UserReceivedRepliesDTO(
            List<UserReceivedRepliesDO> userReceivedRepliesDOList);
    
    UserReceivedRepliesVO userReceivedRepliesDTO2UserReceivedRepliesVO(UserReceivedRepliesDTO userReceivedRepliesDTO);
    
    List<UserReceivedRepliesVO> listUserReceivedRepliesDTO2UserReceivedRepliesVO(
            List<UserReceivedRepliesDTO> userReceivedRepliesDTOList);
    
}
