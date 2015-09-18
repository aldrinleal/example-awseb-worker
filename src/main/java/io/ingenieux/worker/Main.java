package io.ingenieux.worker;

import io.ingenieux.worker.resources.RootResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<ServiceConfiguration> {

  public static void main(String[] args) throws Exception {
    new Main().run(args);
  }

  @Override
  public String getName() {
    return "example-worker-app";
  }

  @Override
  public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
  }

  @Override
  public void run(ServiceConfiguration cfg,
                  Environment env) throws Exception {
    env.jersey().register(new RootResource(cfg));
  }
}
