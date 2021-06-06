package carry.ilearn.controller;

import carry.ilearn.common.response.CommonReturnType;
import carry.ilearn.model.UserRoleModel;
import carry.ilearn.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Carry
 * @since 2021/5/28
 */
@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {
    @Resource
    private AdminService adminService;
    
    @GetMapping("/users")
    public CommonReturnType<?> geUsers() {
        final UserRoleModel users = adminService.getUsers();
        return CommonReturnType.create(users);
    }
    
    @PutMapping("/role")
    public CommonReturnType<?> updateUserRole(@RequestParam Integer userId,
            @RequestParam(required = false) Integer[] ids) {
        adminService.updateUserRole(userId, ids);
        return CommonReturnType.empty();
    }
    
}
