package pl.easyprogramming.bank.domain.account.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.easyprogramming.bank.domain.account.dto.ChargeDTO;
import pl.easyprogramming.bank.domain.account.model.AccountId;
import pl.easyprogramming.bank.domain.account.model.AccountService;
import pl.easyprogramming.bank.domain.account.model.Money;

@RestController
@RequestMapping("account")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.PUT, value = "{accountId}/charge")
    public ResponseEntity charge(@PathVariable("accountId") Long accountId,
                                   @RequestBody() ChargeDTO chargeDTO) {

        accountService.charge(new AccountId(accountId), new Money(chargeDTO));

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{accountId}/transfer/{accountNumber}/charge")
    public ResponseEntity transferMoneyToAnotherAccount(@PathVariable(value = "accountId") String accountId,
                                                     @PathVariable(value = "accountNumber") String accountNumber,
                                                     @RequestBody Money amount) {

        return null;
    }
}
