package com.surepayroll;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Config.Key;
import org.aeonbits.owner.Config.DefaultValue;

@Sources("file:src/test/resources/environment/environment.properties")
public interface Environment extends Config  {
    // ability to run a test against different environments is written here

    @DefaultValue("DEV")
    String environment();

    String jsonFilePath();
    String shared_Path();
    String environmentFile_Path();

}