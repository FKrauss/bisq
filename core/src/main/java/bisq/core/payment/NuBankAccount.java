/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.core.payment;

import bisq.core.payment.payload.NuBankAccountPayload;
import bisq.core.payment.payload.PaymentAccountPayload;
import bisq.core.payment.payload.PaymentMethod;
import bisq.core.payment.payload.NuBankAccountPayload;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;

import bisq.core.locale.Country;
import bisq.core.locale.FiatCurrency;
import bisq.core.payment.payload.NuBankAccountPayload;

public final class NuBankAccount extends PaymentAccount
{
    public NuBankAccount()
    {
        super(PaymentMethod.NU_BANK);
        setSingleTradeCurrency(new FiatCurrency("BRL"));

    }

    @Override
    protected PaymentAccountPayload createPayload()
    {
        return new NuBankAccountPayload(paymentMethod.getId(), id);
    }
    // email
     public String getRecieverEmail()
    {
        return ((NuBankAccountPayload) paymentAccountPayload).getRecieverEmail();
    }
    public void setRecieverEmail(String recieverEmail)
    {
        if (recieverEmail == null) recieverEmail = "";
        ((NuBankAccountPayload) paymentAccountPayload).setRecieverEmail(recieverEmail);
    }
    // cpf
     public String getRecieverCpf()
    {
        return ((NuBankAccountPayload) paymentAccountPayload).getRecieverCpf();
    }
    public void setRecieverCpf(String recieverCpf)
    {
        if (recieverCpf == null) recieverCpf = "";
        ((NuBankAccountPayload) paymentAccountPayload).setRecieverCpf(recieverCpf);
    }

   
    
}
