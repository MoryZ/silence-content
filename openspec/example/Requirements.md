# 用户认证模块

## Purpose

用户认证模块，管理登录、登出和会话维护。

### Requirement: Login Authentication
系统 MUST 在用户提供有效凭证时签发 JWT token。
系统 MUST 在凭证无效时返回 401 错误，且不泄露是用户名还是密码错误。
系统 SHOULD 在连续 5 次失败后触发临时锁定。
系统 MAY 支持"记住我"功能以延长 token 有效期。
