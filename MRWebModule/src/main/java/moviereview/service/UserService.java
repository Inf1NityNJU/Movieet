package moviereview.service;

import moviereview.model.User;
import moviereview.util.LoginState;
import moviereview.util.ResetState;
import moviereview.util.ResultMessage;

/**
 * Created by vivian on 2017/5/7.
 */
public interface UserService {
    /**
     * 登录
     *
     * @param username  用户账号
     * @param password 用户密码
     * @return 当前登录状态
     */
    public ResultMessage signIn(String username, String password);

    /**
     * 登出
     *
     * @return 当前登录状态
     */
    public ResultMessage signOut();

    /**
     * 重置密码
     *
     * @param id     用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 重置密码结果状态
     */
    public ResetState reset(int id, String oldPassword, String newPassword);

    /**
     * 增加用户
     *
     * @param user 新用户
     * @return 是否增加成功
     */
    public ResultMessage signUp(User user);

    /**
     * 根据id查找用户
     *
     * @param id 用户id
     * @return 查到的用户
     */
    public User findById(int id);

    /**
     * 根据username查找用户
     * @param username
     * @return 查到的用户
     */
    public User findByUsername(String username);

    /**
     * 更新用户信息
     *
     * @param user 新用户
     * @return 是否更新成功
     */
    public ResultMessage update(User user);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    public ResultMessage delete(int id);
}
