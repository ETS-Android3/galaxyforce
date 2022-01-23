package com.danosoftware.galaxyforce.utilities;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ShaderProgram {

  int programHandle;
  int vertexShader;
  int fragmentShader;

}
