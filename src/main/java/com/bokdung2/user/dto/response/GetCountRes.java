package com.bokdung2.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class GetCountRes {
    public long received;
    public long chance;

    @Builder
    public GetCountRes(long received, long chance) {
        this.received = received;
        this.chance = chance;
    }
}
