package com.danosoftware.galaxyforce.interfaces;

import java.io.IOException;
import java.io.InputStream;

public interface FileIO {
    InputStream readAsset(String fileName) throws IOException;
}
