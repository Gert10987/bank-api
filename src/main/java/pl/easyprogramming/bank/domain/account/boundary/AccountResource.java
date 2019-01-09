package pl.easyprogramming.bank.domain.account.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.easyprogramming.bank.domain.account.dto.MoneyDTO;
import pl.easyprogramming.bank.domain.account.model.AccountIdentity;
import pl.easyprogramming.bank.domain.account.model.AccountNumber;
import pl.easyprogramming.bank.domain.account.model.AccountService;
import pl.easyprogramming.bank.domain.account.model.Money;

@RestController
@RequestMapping("account")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.PUT, value = "{accountId}/charge")
    public ResponseEntity charge(@PathVariable("accountId") Long accountId,
                                   @RequestBody() MoneyDTO moneyDTO) {

        accountService.charge(new AccountIdentity(accountId), new Money(moneyDTO));

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{accountId}/transfer/{accountNumber}/charge")
    public ResponseEntity transferMoneyToAnotherAccount(@PathVariable(value = "accountId") Long accountId,
                                                     @PathVariable(value = "accountNumber") String accountNumber,
                                                     @RequestBody MoneyDTO moneyDTO) {


        accountService.transfer(new AccountIdentity(accountId), new AccountNumber(accountNumber), new Money(moneyDTO));

        return new ResponseEntity(HttpStatus.OK);
    }
}
