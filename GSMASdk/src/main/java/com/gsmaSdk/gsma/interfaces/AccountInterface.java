package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.account.Account;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.common.GSMAError;

public interface AccountInterface  extends BaseInterface{

    void onAccountSuccess(Account account);

    void onAccountFailure(GSMAError gsmaError);


}
