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
     * @param account  用户账号（Id）
     * @param password 用户密码
     * @return 当前登录状态
     */
    public LoginState login(String account, String password);

    /**
     * 登出
     *
     * @return 当前登录状态
     */
    public LoginState logout();

    /**
     * 重置密码
     *
     * @param account     用户账号（Id）
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 重置密码结果状态
     */
    public ResetState reset(String account, String oldPassword, String newPassword);

    /**
     * 增加用户
     *
     * @param user 新用户
     * @return 是否增加成功
     */
    public ResultMessage add(User user);

    /**
     * 根据ID查找用户
     *
     * @param userID 用户账号（Id）
     * @return 查到的用户
     */
    public User searchByID(String userID);

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
     * @param userID 用户账号（Id）
     * @return 是否删除成功
     */
    public ResultMessage delete(String userID);
}
