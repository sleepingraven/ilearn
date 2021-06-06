package carry.ilearn.service.impl;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.error.EmBusinessError;
import carry.ilearn.converter.DiscussionConverter;
import carry.ilearn.dao.*;
import carry.ilearn.dataobject.*;
import carry.ilearn.model.StatementWrapperModel;
import carry.ilearn.query.*;
import carry.ilearn.service.DiscussionService;
import carry.ilearn.service.datatransferobject.CommentDTO;
import carry.ilearn.service.datatransferobject.PostDTO;
import carry.ilearn.service.datatransferobject.UserReceivedRepliesDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class DiscussionServiceImpl implements DiscussionService {
    @Resource
    private PostDao postDao;
    @Resource
    private StatementDao statementDao;
    @Resource
    private DiscussionConverter discussionConverter;
    @Resource
    private CommentDao commentDao;
    @Resource
    private UserLikeDao userLikeDao;
    @Resource
    private UserReceivedRepliesDao userReceivedRepliesDao;
    @Resource
    private UserReceivedRepliesUnreadDao userReceivedRepliesUnreadDao;
    
    @Override
    public void addPost(PostPublishmentQuery postPublishmentQuery) {
        final StatementDO statementDO = new StatementDO();
        statementDO.setContent(postPublishmentQuery.getContent());
        statementDO.setUserId(postPublishmentQuery.getUserId());
        statementDao.insertSelective(statementDO);
        
        final PostDO postDO = new PostDO();
        postDO.setTitle(postPublishmentQuery.getTitle());
        postDO.setCourseId(postPublishmentQuery.getCourseId());
        postDO.setStatementId(statementDO.getId());
        postDao.insertSelective(postDO);
    }
    
    @Override
    public StatementWrapperModel<PostDTO> getByPage(DiscussionPageQuery discussionPageQuery) {
        final List<PostDO> postDOS = postDao.selectByCourseIdPaged(discussionPageQuery.getCourseId(),
                                                                   discussionPageQuery.getPageIdx() *
                                                                   discussionPageQuery.getPageSize(),
                                                                   discussionPageQuery.getPageSize());
        
        final StatementWrapperModel<PostDTO> statementWrapperModel = new StatementWrapperModel<>();
        if (discussionPageQuery.getUserId() != null) {
            List<Integer> statementIdList = postDOS.stream().map(PostDO::getStatementId).collect(Collectors.toList());
            statementIdList =
                    userLikeDao.selectByUserIdAndStatementIdList(discussionPageQuery.getUserId(), statementIdList);
            statementWrapperModel.setLikedStatementIdList(statementIdList);
        }
        
        statementWrapperModel.setWrapperList(discussionConverter.listPostDO2PostDTO(postDOS));
        return statementWrapperModel;
    }
    
    @Override
    public Integer addReply(DiscussionReplyQuery discussionReplyQuery) throws BusinessException {
        if (discussionReplyQuery.getLevel() < 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "参数错误");
        }
        final StatementDO toStatement;
        // 寻找 postId
        final int postId, rootId;
        CommentDO referCommentDO;
        switch (discussionReplyQuery.getLevel()) {
            case 1:
                // 如果是一级，通过 replyTo 找
                postId = discussionReplyQuery.getReplyTo();
                rootId = -1;
                final PostDO postDO = postDao.selectByPrimaryKey(postId);
                toStatement = postDO.getStatementDO();
                break;
            case 2:
                // 如果是二级，通过 replyTo 找到一级，再用 replyToId 找
                referCommentDO = commentDao.selectByPrimaryKey(discussionReplyQuery.getReplyTo());
                if (referCommentDO == null || referCommentDO.getLevel() != discussionReplyQuery.getLevel() - 1) {
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "参数错误");
                }
                postId = referCommentDO.getReplyToId();
                rootId = referCommentDO.getId();
                toStatement = referCommentDO.getStatementDO();
                break;
            default:
                // 如果是二级以上，通过 replyTo 找到上一级，用 rootId 找到一级，再用 replyToId 找
                referCommentDO = commentDao.selectByPrimaryKey(discussionReplyQuery.getReplyTo());
                if (referCommentDO == null || referCommentDO.getLevel() != discussionReplyQuery.getLevel() - 1) {
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "参数错误");
                }
                toStatement = referCommentDO.getStatementDO();
                rootId = referCommentDO.getRootId();
                referCommentDO = commentDao.selectByPrimaryKey(rootId);
                postId = referCommentDO.getReplyToId();
                break;
        }
        final int rows;
        if (discussionReplyQuery.getLevel() == 1) {
            rows = postDao.increaseFirstLevelReply(postId);
        } else {
            rows = postDao.increaseReply(postId);
        }
        if (rows == 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "参数错误");
        }
        
        final StatementDO statementDO = new StatementDO();
        statementDO.setContent(discussionReplyQuery.getContent());
        statementDO.setUserId(discussionReplyQuery.getUserId());
        statementDao.insertSelective(statementDO);
        
        final CommentDO commentDO = new CommentDO();
        commentDO.setRootId(rootId);
        commentDO.setLevel(discussionReplyQuery.getLevel());
        commentDO.setStatementId(statementDO.getId());
        commentDO.setReplyToId(discussionReplyQuery.getReplyTo());
        commentDao.insertSelective(commentDO);
        
        if (!toStatement.getUserId().equals(discussionReplyQuery.getUserId())) {
            final UserReceivedRepliesDO userReceivedRepliesDO = new UserReceivedRepliesDO();
            userReceivedRepliesDO.setPostId(postId);
            userReceivedRepliesDO.setUserToId(toStatement.getUserId());
            userReceivedRepliesDO.setCommentFromId(commentDO.getId());
            userReceivedRepliesDao.insertSelective(userReceivedRepliesDO);
            final UserReceivedRepliesUnreadDO userReceivedRepliesUnreadDO = new UserReceivedRepliesUnreadDO();
            userReceivedRepliesUnreadDO.setReceivedId(userReceivedRepliesDO.getId());
            userReceivedRepliesUnreadDao.insertSelective(userReceivedRepliesUnreadDO);
        }
        
        return commentDO.getId();
    }
    
    @Override
    public Integer getReplyReceivedSnapshotBeginId(Integer userId) {
        return userReceivedRepliesDao.selectReplyReceivedSnapshotBeginId(userId);
    }
    
    @Override
    public List<UserReceivedRepliesDTO> getReplyReceived(ReplyReceivedPageQuery replyReceivedPageQuery) {
        final List<UserReceivedRepliesDO> userReceivedRepliesDOS =
                userReceivedRepliesDao.selectByUserToIdPaged(replyReceivedPageQuery.getUserId(),
                                                             replyReceivedPageQuery.getBeginId(),
                                                             replyReceivedPageQuery.getOffset(), 3);
        final List<UserReceivedRepliesDTO> userReceivedRepliesDTOS =
                discussionConverter.listUserReceivedRepliesDO2UserReceivedRepliesDTO(userReceivedRepliesDOS);
        return userReceivedRepliesDTOS;
    }
    
    @Override
    public Integer reduceUnreadReceived(ReplyReceivedPageQuery replyReceivedPageQuery) {
        userReceivedRepliesDao.deleteUnread(replyReceivedPageQuery.getUserId(), replyReceivedPageQuery.getBeginId());
        return getReplyReceivedSnapshotBeginId(replyReceivedPageQuery.getUserId());
    }
    
    @Override
    public StatementWrapperModel<CommentDTO> getRepliesByPage(ReplyPageQuery replyPageQuery) {
        final List<CommentFirstLevelDO> commentDOS =
                commentDao.selectFirstLevelByPostIdPaged(replyPageQuery.getPostId(),
                                                         replyPageQuery.getPageIdx() * replyPageQuery.getPageSize(),
                                                         replyPageQuery.getPageSize(), 0,
                                                         replyPageQuery.getSecondPageSize());
        
        final StatementWrapperModel<CommentDTO> statementWrapperModel = new StatementWrapperModel<>();
        if (replyPageQuery.getUserId() != null) {
            List<Integer> statementIdList = commentDOS.stream()
                                                      .flatMap(c -> Stream.concat(Stream.of(c),
                                                                                  c.getCommentDOList().stream())
                                                                          .map(CommentDO::getStatementId))
                                                      .collect(Collectors.toList());
            statementIdList = userLikeDao.selectByUserIdAndStatementIdList(replyPageQuery.getUserId(), statementIdList);
            statementWrapperModel.setLikedStatementIdList(statementIdList);
        }
        
        statementWrapperModel.setWrapperList(discussionConverter.listFirstLeveCommentDO2CommentDTO(commentDOS));
        return statementWrapperModel;
    }
    
    @Override
    public StatementWrapperModel<CommentDTO> getRepliesByPage(ReplySecondLevelPageQuery replySecondLevelPageQuery) {
        final List<CommentDO> commentDOS =
                commentDao.selectSecondLevelByCommentIdPaged(replySecondLevelPageQuery.getCommentId(),
                                                             replySecondLevelPageQuery.getSecondPageIdx() *
                                                             replySecondLevelPageQuery.getSecondPageSize(),
                                                             replySecondLevelPageQuery.getSecondPageSize());
        
        final StatementWrapperModel<CommentDTO> statementWrapperModel = new StatementWrapperModel<>();
        if (replySecondLevelPageQuery.getUserId() != null) {
            List<Integer> statementIdList =
                    commentDOS.stream().map(CommentDO::getStatementId).collect(Collectors.toList());
            statementIdList = userLikeDao.selectByUserIdAndStatementIdList(replySecondLevelPageQuery.getUserId(),
                                                                           statementIdList);
            statementWrapperModel.setLikedStatementIdList(statementIdList);
        }
        
        statementWrapperModel.setWrapperList(discussionConverter.listCommentDO2CommentDTO(commentDOS));
        return statementWrapperModel;
    }
    
    @Override
    public void updateLike(UserLikeQuery userLikeQuery) {
        final StatementDO statementDO = statementDao.selectByPrimaryKey(userLikeQuery.getStatementId());
        if (userLikeQuery.getLike()) {
            final UserLikeDO userLikeDO = new UserLikeDO();
            userLikeDO.setUserId(userLikeQuery.getUserId());
            userLikeDO.setStatementId(userLikeQuery.getStatementId());
            userLikeDao.insertSelective(userLikeDO);
            statementDO.setTotalLikes(statementDO.getTotalLikes() + 1);
        } else {
            userLikeDao.deleteByUserIdAndStatementId(userLikeQuery.getUserId(), userLikeQuery.getStatementId());
            statementDO.setTotalLikes(statementDO.getTotalLikes() - 1);
        }
        statementDao.updateByPrimaryKeySelective(statementDO);
    }
    
}
