package com.ecjtu.onlinemusic.entity;


import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collection;

@Data
public class Consumer implements Serializable {
    @NotNull(message = "用户id不能为空。", groups = {update.class})
    private Integer id;
    private String name;
    @NotNull(message = "用户邮箱mail不能为空。", groups = {insert.class})  // 前端数据验证。   @Validated分组验证。
    @Email(message = "用户邮箱mail有误。", groups = {insert.class, update.class})
    private String mail;
    private String phone;
    @NotNull(message = "用户密码password不能为空。", groups = {insert.class})
    private String password;
    private String pictureUrl;

    private Integer score;

//    /**
//     * 获取用户权限
//     *
//     * @return
//     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    /**
//     * 获取用户密码
//     *
//     * @return
//     */
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    /**
//     * 获取用户名称
//     *
//     * @return
//     */
//    @Override
//    public String getUsername() {
//        return this.mail;
//    }
//
//    /**
//     * 验证当前用户是否过期
//     * 用户没有过期返回 true 过期返回false
//     *
//     * @return
//     */
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    /**
//     * 当前用户是否锁定
//     * 没有锁定返回true 锁定了返回false
//     *
//     * @return
//     */
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    /**
//     * 密码是否过期
//     * 没有过期返回 true 过期了返回false
//     *
//     * @return
//     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    /**
//     * 当前用户是否启用
//     * 启用返回true 禁用返回false
//     *
//     * @return
//     */
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
