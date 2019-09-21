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

package bisq.core.payment.payload;

import bisq.core.locale.Res;

import com.google.protobuf.Message;

import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@ToString
@Setter
@Getter
@Slf4j
public final class NuBankAccountPayload extends PaymentAccountPayload {
    // email
    private String recieverEmail = "";
    // cpf
    private String recieverCpf = "";


    public JapanBankAccountPayload(String paymentMethod, String id) {
        super(paymentMethod, id);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // PROTO BUFFER
    ///////////////////////////////////////////////////////////////////////////////////////////

    private NuBankAccountPayload(String paymentMethod,
                                  String id,
                                  String recieverEmail,
                                  String recieverCpf,
                                  long maxTradePeriod,
                                  Map<String, String> excludeFromJsonDataMap) {
        super(paymentMethod,
                id,
                maxTradePeriod,
                excludeFromJsonDataMap);

        this.recieverEmail = recieverEmail;
        this.recieverCpf = recieverCpf;

    }

    @Override
    public Message toProtoMessage() {
        return getPaymentAccountPayloadBuilder()
                .setJapanBankAccountPayload(
                        protobuf.JapanBankAccountPayload.newBuilder()
                        .setRecieverEmail(recieverEmail)
                        .setRecieverCph(recieverCpf)
                ).build();
    }

    public static NuBankAccountPayload fromProto(protobuf.PaymentAccountPayload proto) {
        protobuf.NuBankAccountPayload nuBankAccountPayload = proto.getNuBankAccountPayload();
        return new JapanBankAccountPayload(proto.getPaymentMethodId(),
                proto.getId(),
                nuBankAccountPayload.getRecieverEmail(),
                nuBankAccountPayload.getRecieverCpf(),
                proto.getMaxTradePeriod(),
                CollectionUtils.isEmpty(proto.getExcludeFromJsonDataMap()) ? null : new HashMap<>(proto.getExcludeFromJsonDataMap()));
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String getPaymentDetails()
    {
        return Res.get(paymentMethodId) + " - " + getPaymentDetailsForTradePopup().replace("\n", ", ");
    }

    @Override
    public String getPaymentDetailsForTradePopup()
    {
        return
        Res.get("payment.japan.bank") + ": " + bankName + "(" + bankCode + ")\n" +
        Res.get("payment.japan.branch") + ": " +  bankBranchName + "(" + bankBranchCode + ")\n" +
        Res.get("payment.japan.account") + ": " + bankAccountType + " " + bankAccountNumber + "\n" + Res.get("payment.japan.recipient") + ": " + bankAccountName;
    }


    @Override
    public byte[] getAgeWitnessInputData() {
        String all = this.bankName + this.bankBranchName + this.bankAccountType + this.bankAccountNumber + this.bankAccountName;
        return super.getAgeWitnessInputData(all.getBytes(Charset.forName("UTF-8")));
    }
}
