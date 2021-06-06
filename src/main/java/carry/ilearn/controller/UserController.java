package carry.ilearn.controller;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.response.CommonReturnType;
import carry.ilearn.converter.UserConverter;
import carry.ilearn.query.*;
import carry.ilearn.service.FileService;
import carry.ilearn.service.UserService;
import carry.ilearn.service.UserStateService;
import carry.ilearn.service.datatransferobject.FileDTO;
import carry.ilearn.service.datatransferobject.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

/**
 * @author Carry
 * @since 2021/5/14
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private FileService fileService;
    @Resource
    private UserStateService userStateService;
    @Resource
    private UserConverter userConverter;
    
    /**
     * 用户注册接口
     */
    @PostMapping(path = "/sign-up")
    public CommonReturnType<?> signUp(@RequestBody SignQuery signQuery) throws Exception {
        try {
            final FileCreateQuery fileCreateQuery = new FileCreateQuery();
            fileCreateQuery.setCategory("images");
            fileCreateQuery.setExtension("svg");
            fileCreateQuery.setPathPrefix("archive");
            final FileDTO file = fileService.createFile(fileCreateQuery);
            try (final FileOutputStream os = new FileOutputStream(file.getFile())) {
                final Random random = new Random();
                final int i = random.nextInt(256 * 256 * 256);
                String color = Integer.toHexString(i);
                String initAvatar =
                        "<svg\n" + "    class=\"bd-placeholder-img mr-2 rounded-circle\"\n" + "    width=\"32\"\n" +
                        "    height=\"32\"\n" + "    xmlns=\"http://www.w3.org/2000/svg\"\n" + "    role=\"img\"\n" +
                        "    aria-label=\"Placeholder: 32x32\"\n" + "    preserveAspectRatio=\"xMidYMid slice\"\n" +
                        "    focusable=\"false\"\n" + "  >\n" + "    <title>Placeholder</title>\n" +
                        "    <rect width=\"100%\" height=\"100%\" fill=\"#" + color + "\" />\n" +
                        "    <text x=\"50%\" y=\"50%\" fill=\"#" + color + "\" dy=\".3em\">32x32</text>\n" + "  </svg>";
                os.write(initAvatar.getBytes());
            }
            
            signQuery.setAvatarUrl(file.getUrl());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        userService.doSignUp(signQuery);
        
        return CommonReturnType.empty();
    }
    
    @PostMapping(path = "/upload-avatar")
    public CommonReturnType<?> uploadAvatar(@RequestParam(name = "file") MultipartFile file) {
        UserDTO loginUser = userStateService.getLoginUser();
        String url;
        try {
            url = fileService.saveFile(new FileSaveQuery(file, "images")).getUrl();
            UserAvatarQuery userAvatarQuery = new UserAvatarQuery();
            userAvatarQuery.setId(loginUser.getId());
            userAvatarQuery.setAvatarUrl(url);
            userService.updateAvatarUrl(userAvatarQuery);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonReturnType.withError("上传失败");
        }
        loginUser.setAvatarUrl(url);
        userStateService.saveUser(loginUser);
        return CommonReturnType.create(url);
    }
    
    @GetMapping(path = "/profile")
    public CommonReturnType<?> getProfile() {
        UserDTO loginUser = userStateService.getLoginUser();
        return CommonReturnType.create(userConverter.userDTO2UserProfileVO(loginUser));
    }
    
    @PatchMapping(path = "/profile")
    public CommonReturnType<?> updateProfile(@RequestBody UserProfileQuery userProfileQuery) {
        UserDTO loginUser = userStateService.getLoginUser();
        userProfileQuery.setId(loginUser.getId());
        userService.updateUser(userProfileQuery);
        userConverter.userProfileQuery2UserDTO(userProfileQuery, loginUser);
        userStateService.saveUser(loginUser);
        return CommonReturnType.empty();
    }
    
    @GetMapping(path = "/emails")
    public CommonReturnType<?> getEmails() {
        UserDTO loginUser = userStateService.getLoginUser();
        List<String> emails = userService.getEmails(loginUser.getId());
        
        return CommonReturnType.create(emails);
    }
    
    @PostMapping(path = "/email")
    public CommonReturnType<?> addEmail(@RequestBody String addiEmail) throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        UserEmailQuery userEmailQuery = new UserEmailQuery(loginUser.getId(), addiEmail);
        userService.addEmail(userEmailQuery);
        return CommonReturnType.empty();
    }
    
    @DeleteMapping(path = "/email")
    public CommonReturnType<?> removeEmail(@RequestBody String email) throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        UserEmailQuery userEmailQuery = new UserEmailQuery(loginUser.getId(), email);
        userService.removeEmail(userEmailQuery);
        return CommonReturnType.empty();
    }
    
    @GetMapping(path = "/avatar-url")
    public CommonReturnType<?> getUserAvatarUrlByEmail(@RequestParam String email) throws BusinessException {
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null) {
            return CommonReturnType.empty();
        } else {
            return CommonReturnType.create(userDTO.getAvatarUrl());
        }
    }
    
    @PostMapping(path = "/main-email")
    public CommonReturnType<?> setMainEmail(@RequestBody String email) throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        UserEmailQuery userEmailQuery = new UserEmailQuery(loginUser.getId(), email);
        userService.updateMainEmail(userEmailQuery);
        return CommonReturnType.empty();
    }
    
    @PutMapping(path = "/password")
    public CommonReturnType<?> changePassword(@RequestBody UserPasswordQuery userPasswordQuery) throws Exception {
        UserDTO loginUser = userStateService.getLoginUser();
        userPasswordQuery.setId(loginUser.getId());
        userService.changePassword(userPasswordQuery);
        return CommonReturnType.empty();
    }
    
}
