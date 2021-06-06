package carry.ilearn.converter;

import carry.ilearn.controller.viewobject.UserCommonVO;
import carry.ilearn.controller.viewobject.UserEmailVO;
import carry.ilearn.controller.viewobject.UserProfileVO;
import carry.ilearn.dataobject.UserDO;
import carry.ilearn.query.UserProfileQuery;
import carry.ilearn.service.datatransferobject.UserDTO;
import carry.ilearn.service.datatransferobject.UserEmailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Mapper(componentModel = "spring")
public interface UserConverter {
    
    UserDTO userDO2UserDTO(UserDO userDO);
    
    UserProfileVO userDTO2UserProfileVO(UserDTO userDO);
    
    UserDO userProfileQuery2UserDO(UserProfileQuery userProfileQuery);
    
    void userProfileQuery2UserDTO(UserProfileQuery userProfileQuery, @MappingTarget UserDTO userDO);
    
    @Mapping(source = "secondaryEmails", target = "otherEmails")
    UserEmailVO userEmailDTO2UserEmailVO(UserEmailDTO userEmailDTO);
    
    UserCommonVO convert2UserCommonVO(UserDTO userDTO);
    
}
