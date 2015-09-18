package io.ingenieux.worker;

import io.dropwizard.Configuration;

public class ServiceConfiguration extends Configuration {
  String topicArn;

  public String getTopicArn() {
    return topicArn;
  }

  public void setTopicArn(String topicArn) {
    this.topicArn = topicArn;
  }
}
