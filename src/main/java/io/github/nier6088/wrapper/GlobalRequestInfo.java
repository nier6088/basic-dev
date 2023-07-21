package io.github.nier6088.wrapper;

import lombok.Data;

import java.util.Set;

@Data
public class GlobalRequestInfo {
    private String userToken;
    private Set<String> roles;
    private Set<String> permissions;
}

