package carry.ilearn.service;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.model.StatementWrapperModel;
import carry.ilearn.query.*;
import carry.ilearn.service.datatransferobject.CommentDTO;
import carry.ilearn.service.datatransferobject.PostDTO;
import carry.ilearn.service.datatransferobject.UserReceivedRepliesDTO;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/22
 */
public interface DiscussionService {
    
    void addPost(PostPublishmentQuery postPublishmentQuery);
    
    StatementWrapperModel<PostDTO> getByPage(DiscussionPageQuery discussionPageQuery);
    
    Integer addReply(DiscussionReplyQuery discussionReplyQuery) throws BusinessException;
    
    Integer getReplyReceivedSnapshotBeginId(Integer userId);
    
    List<UserReceivedRepliesDTO> getReplyReceived(ReplyReceivedPageQuery replyReceivedPageQuery);
    
    Integer reduceUnreadReceived(ReplyReceivedPageQuery replyReceivedPageQuery);
    
    StatementWrapperModel<CommentDTO> getRepliesByPage(ReplyPageQuery replyPageQuery);
    
    StatementWrapperModel<CommentDTO> getRepliesByPage(ReplySecondLevelPageQuery replySecondLevelPageQuery);
    
    void updateLike(UserLikeQuery userLikeQuery);
    
}
