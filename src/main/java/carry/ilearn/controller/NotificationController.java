package carry.ilearn.controller;

import carry.ilearn.common.response.CommonReturnType;
import carry.ilearn.controller.viewobject.UserReceivedRepliesVO;
import carry.ilearn.converter.DiscussionConverter;
import carry.ilearn.query.ReplyReceivedPageQuery;
import carry.ilearn.service.DiscussionService;
import carry.ilearn.service.UserStateService;
import carry.ilearn.service.datatransferobject.UserDTO;
import carry.ilearn.service.datatransferobject.UserReceivedRepliesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/28
 */
@Slf4j
@RequestMapping("/notification")
@RestController
public class NotificationController {
    @Resource
    private UserStateService userStateService;
    @Resource
    private DiscussionService discussionService;
    @Resource
    private DiscussionConverter discussionConverter;
    
    @GetMapping("/reply-received-begin-id")
    public CommonReturnType<?> getReplyReceivedSnapshotBeginId() {
        final UserDTO loginUser = userStateService.getLoginUser();
        final Integer replyReceivedUnreadBeginId = discussionService.getReplyReceivedSnapshotBeginId(loginUser.getId());
        return CommonReturnType.create(replyReceivedUnreadBeginId);
    }
    
    @GetMapping("/reply-received")
    public CommonReturnType<List<UserReceivedRepliesVO>> getReplyReceived(
            ReplyReceivedPageQuery replyReceivedPageQuery) {
        final UserDTO loginUser = userStateService.getLoginUser();
        replyReceivedPageQuery.setUserId(loginUser.getId());
        final List<UserReceivedRepliesDTO> userReceivedRepliesDTOS =
                discussionService.getReplyReceived(replyReceivedPageQuery);
        final List<UserReceivedRepliesVO> userReceivedRepliesVOS =
                discussionConverter.listUserReceivedRepliesDTO2UserReceivedRepliesVO(userReceivedRepliesDTOS);
        return CommonReturnType.create(userReceivedRepliesVOS);
    }
    
    @PostMapping("/reduce-unread-received")
    public CommonReturnType<?> reduceUnreadReceived(@RequestBody Integer beginId) {
        final UserDTO loginUser = userStateService.getLoginUser();
        final ReplyReceivedPageQuery replyReceivedPageQuery = new ReplyReceivedPageQuery();
        replyReceivedPageQuery.setBeginId(beginId);
        replyReceivedPageQuery.setUserId(loginUser.getId());
        
        beginId = discussionService.reduceUnreadReceived(replyReceivedPageQuery);
        return CommonReturnType.create(beginId);
    }
    
}
