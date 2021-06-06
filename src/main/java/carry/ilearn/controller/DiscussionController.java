package carry.ilearn.controller;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.response.CommonReturnType;
import carry.ilearn.controller.viewobject.CommentVO;
import carry.ilearn.controller.viewobject.PostVO;
import carry.ilearn.converter.DiscussionConverter;
import carry.ilearn.model.StatementWrapperModel;
import carry.ilearn.query.*;
import carry.ilearn.service.DiscussionService;
import carry.ilearn.service.FileService;
import carry.ilearn.service.UserStateService;
import carry.ilearn.service.datatransferobject.CommentDTO;
import carry.ilearn.service.datatransferobject.PostDTO;
import carry.ilearn.service.datatransferobject.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Slf4j
@RequestMapping("/discussion")
@RestController
public class DiscussionController {
    @Resource
    private UserStateService userStateService;
    @Resource
    private DiscussionService discussionService;
    @Resource
    private DiscussionConverter discussionConverter;
    @Resource
    private FileService fileService;
    
    @PostMapping(path = "/publish")
    public CommonReturnType<?> publishDiscussion(@RequestBody PostPublishmentQuery postPublishmentQuery) {
        final UserDTO loginUser = userStateService.getLoginUser();
        postPublishmentQuery.setUserId(loginUser.getId());
        discussionService.addPost(postPublishmentQuery);
        return CommonReturnType.empty();
    }
    
    @GetMapping(path = "/list")
    public CommonReturnType<?> getDiscussions(DiscussionPageQuery discussionPageQuery) {
        final UserDTO loginUser = userStateService.getLoginUser();
        if (loginUser != null) {
            discussionPageQuery.setUserId(loginUser.getId());
        }
        final StatementWrapperModel<PostDTO> modelPostDTO = discussionService.getByPage(discussionPageQuery);
        
        final StatementWrapperModel<PostVO> modelPostVO = new StatementWrapperModel<>();
        modelPostVO.setWrapperList(discussionConverter.listPostDTO2PostVO(modelPostDTO.getWrapperList()));
        modelPostVO.setLikedStatementIdList(modelPostDTO.getLikedStatementIdList());
        return CommonReturnType.create(modelPostVO);
    }
    
    @PostMapping(path = "/reply")
    public CommonReturnType<?> publishReply(@RequestBody DiscussionReplyQuery discussionReplyQuery)
            throws BusinessException {
        final UserDTO loginUser = userStateService.getLoginUser();
        discussionReplyQuery.setUserId(loginUser.getId());
        final Integer commentId = discussionService.addReply(discussionReplyQuery);
        return CommonReturnType.create(commentId);
    }
    
    @GetMapping(path = "/replies/list")
    public CommonReturnType<?> getRepliesList(ReplyPageQuery replyPageQuery) {
        final UserDTO loginUser = userStateService.getLoginUser();
        if (loginUser != null) {
            replyPageQuery.setUserId(loginUser.getId());
        }
        final StatementWrapperModel<CommentDTO> modelCommentDTO = discussionService.getRepliesByPage(replyPageQuery);
        
        final StatementWrapperModel<CommentVO> modelCommentVO = new StatementWrapperModel<>();
        modelCommentVO.setWrapperList(discussionConverter.listCommentDTO2CommentVO(modelCommentDTO.getWrapperList()));
        modelCommentVO.setLikedStatementIdList(modelCommentDTO.getLikedStatementIdList());
        return CommonReturnType.create(modelCommentVO);
    }
    
    @GetMapping(path = "/replies/sub/list")
    public CommonReturnType<?> getRepliesList(ReplySecondLevelPageQuery replySecondLevelPageQuery) {
        final UserDTO loginUser = userStateService.getLoginUser();
        if (loginUser != null) {
            replySecondLevelPageQuery.setUserId(loginUser.getId());
        }
        final StatementWrapperModel<CommentDTO> modelCommentDTO =
                discussionService.getRepliesByPage(replySecondLevelPageQuery);
        
        final StatementWrapperModel<CommentVO> modelCommentVO = new StatementWrapperModel<>();
        modelCommentVO.setWrapperList(discussionConverter.listCommentDTO2CommentVO(modelCommentDTO.getWrapperList()));
        modelCommentVO.setLikedStatementIdList(modelCommentDTO.getLikedStatementIdList());
        return CommonReturnType.create(modelCommentVO);
    }
    
    @PostMapping(path = "/like")
    public CommonReturnType<?> performLike(@RequestBody UserLikeQuery userLikeQuery) {
        final UserDTO loginUser = userStateService.getLoginUser();
        userLikeQuery.setUserId(loginUser.getId());
        discussionService.updateLike(userLikeQuery);
        return CommonReturnType.empty();
    }
    
    @PostMapping(path = "/upload-discussion-img")
    public CommonReturnType<?> uploadLearnImg(@RequestParam(name = "image") MultipartFile file) {
        userStateService.getLoginUser();
        try {
            String url = fileService.saveFile(new FileSaveQuery(file, "images")).getUrl();
            return CommonReturnType.create(url);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonReturnType.withError("上传失败");
        }
    }
    
}
