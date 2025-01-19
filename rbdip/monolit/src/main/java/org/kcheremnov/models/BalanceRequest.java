package org.kcheremnov.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequest {
    private static final long serialVersionUID = 1L;

    private Long change;
    private Long userId;

    public String toString() {
        String converted = change.toString() + " " + userId.toString();

        return converted;
    }

    public static BalanceRequest fromString(String convertedString) {
        String[] splitted = convertedString.split(" ");

        Long change = Long.parseLong(splitted[0]);
        Long userId = Long.parseLong(splitted[1]);

        return new BalanceRequest(change, userId);
    }
}
