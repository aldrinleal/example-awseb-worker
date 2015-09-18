package io.ingenieux.worker.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseResource {

  public static final String ID_MASK = "{id}";

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
