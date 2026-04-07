# 用户认证场景

## 登录

#### Scenario: Successful Login

- **Given** 用户名 "alice" 存在且密码正确
- **When** 用户提交登录请求
- **Then** 系统返回 200 和有效的 JWT token
- **And** token 有效期为 24 小时

#### Scenario: Invalid Password

- **Given** 用户名 "alice" 存在但密码错误
- **When** 用户提交登录请求
- **Then** 系统返回 401
- **And** 错误信息不区分"用户不存在"和"密码错误"
