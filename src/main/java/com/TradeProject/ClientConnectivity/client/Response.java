package com.TradeProject.ClientConnectivity.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String name;
    private Object data;
    private String status;
    private int code;
}
