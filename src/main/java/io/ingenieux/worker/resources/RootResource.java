package io.ingenieux.worker.resources;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import io.ingenieux.worker.ServiceConfiguration;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource extends BaseResource {

  final ObjectMapper objectMapper;

  final AmazonSNS snsClient;

  final String topicArn;

  public RootResource(ServiceConfiguration serviceConfiguration) {
    this.topicArn = serviceConfiguration.getTopicArn();

    objectMapper = new ObjectMapper();

    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    snsClient = new AmazonSNSClient();
  }

  @GET
  public JsonNode doCheck() throws Exception {
    return objectMapper.convertValue("ok", JsonNode.class);
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  public JsonNode doSomething(@Context HttpHeaders headers, JsonNode jsonNode) throws Exception {
    final ObjectNode
        payloadNode =
        objectMapper.createObjectNode();

    payloadNode
        .set("headers", objectMapper.convertValue(headers.getRequestHeaders(),
                                                  ObjectNode.class));
    payloadNode.set("body", jsonNode);

    logger.info("payloadNode: {}", objectMapper.writeValueAsString(payloadNode));

    PublishRequest publishRequest = new PublishRequest();

    publishRequest.setMessage(objectMapper.writeValueAsString(payloadNode));
    publishRequest.setTargetArn(topicArn);

    snsClient.publish(publishRequest);

    return objectMapper.convertValue("ok", JsonNode.class);
  }
}
