package com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.impl;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.tool.RpcAnnotation;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.ITellPhoneService;

@RpcAnnotation(ITellPhoneService.class)
public class TellPhoneService implements ITellPhoneService {
    @Override
    public String tell(String name) {
        return "tell phone to " + name;
    }
}
