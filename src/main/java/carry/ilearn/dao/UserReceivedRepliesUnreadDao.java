package carry.ilearn.dao;

import carry.ilearn.dataobject.UserReceivedRepliesUnreadDO;

public interface UserReceivedRepliesUnreadDao {
    int insert(UserReceivedRepliesUnreadDO record);

    int insertSelective(UserReceivedRepliesUnreadDO record);
}