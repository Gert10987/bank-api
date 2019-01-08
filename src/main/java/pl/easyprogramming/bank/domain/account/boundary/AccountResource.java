package pl.easyprogramming.bank.domain.account.boundary;

import org.springframework.web.bind.annotation.*;
import pl.easyprogramming.bank.domain.account.model.Money;

import javax.xml.ws.Response;

@RestController
@RequestMapping("account")
public class AccountResource {

    @RequestMapping(method = RequestMethod.PUT, value = "{accountId}/charge")
    public Response<String> charge(@PathVariable("accountId") String accountId,
                                   @RequestBody() Money amount) {

        return null;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{accountId}/transfer/{accountNumber}/charge")
    public Response<String> transferMoneyToAnotherAccount(@PathVariable(value = "accountId") String accountId,
                                                     @PathVariable(value = "accountNumber") String accountNumber,
                                                     @RequestBody Money amount) {

        return null;
    }
}
