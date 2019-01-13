package pl.easyprogramming.bank.domain.account.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.easyprogramming.bank.domain.account.model.Account;
import pl.easyprogramming.bank.domain.account.model.AccountIdentity;
import pl.easyprogramming.bank.domain.account.model.AccountNumber;
import pl.easyprogramming.bank.domain.account.model.AccountService;
import pl.easyprogramming.bank.domain.common.model.Money;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("account")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.PUT, value = "{accountId}/charge")
    public ResponseEntity charge(@PathVariable("accountId") Long accountId,
                                 @RequestBody() Money money) {

        accountService.charge(new AccountIdentity(accountId), money);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{accountId}/transfer/{accountNumber}/charge")
    public ResponseEntity transferMoneyToAnotherAccount(@PathVariable(value = "accountId") Long accountId,
                                                        @PathVariable(value = "accountNumber") String accountNumber,
                                                        @RequestBody Money money) {

        accountService.transfer(new AccountIdentity(accountId), new AccountNumber(accountNumber), money);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{accountId}")
    public ResponseEntity details(@PathVariable(value = "accountId") Long accountId) {

        Account account = accountService.details(accountId);

        return new ResponseEntity(account, HttpStatus.OK);
    }
}
