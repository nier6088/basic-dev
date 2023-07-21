package io.github.nier6088.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class BaseReq {

    @JsonProperty("userToken")
    private Long userToken;

    public BaseReq() {


    }


}
