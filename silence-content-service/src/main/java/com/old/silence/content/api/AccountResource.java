package com.old.silence.content.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.domain.model.Account;
import com.old.silence.content.infrastructure.persistence.dao.AccountDao;
import com.old.silence.content.util.FileReadUtils;
import com.old.silence.json.JacksonMapper;

/**
 * @author MurrayZhang
 */
@RequestMapping("/api/v1")
@RestController
public class AccountResource {

    private final AccountDao accountDao;

    public AccountResource(AccountDao accountDao) {
           this.accountDao = accountDao;
    }

    @GetMapping("accounts")
    public void bulk() {
        FileReadUtils fileReadUtils = FileReadUtils.getInstance();
        String content = fileReadUtils.readContent("accounts.json");
        var accounts = JacksonMapper.getSharedInstance().fromCollectionJson(content, Account.class);

        accountDao.insertAll(accounts);
    }
}
