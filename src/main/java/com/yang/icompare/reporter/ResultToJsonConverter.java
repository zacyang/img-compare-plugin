package com.yang.icompare.reporter;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yang.icompare.core.Result;

public class ResultToJsonConverter {
    private static final String RESULTS = "results";
    private static final String SUMMARY = "summary";
    private final ObjectMapper mapper = new ObjectMapper();

    public String generateJson(final List<Result> result) {
        final ObjectNode resultJson = mapper.createObjectNode();

        updateMetaDataTag(resultJson, result);

        updateResultTags(resultJson, result);

        return toString(resultJson);
    }

    private String toString(ObjectNode resultJson) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultJson);
        } catch (JsonProcessingException e) {
            return error(e);
        }
    }

    private void updateResultTags(final ObjectNode resultJson, List<Result> result) {
        List<JsonNode> allResultNodes = result.stream().map(r -> {
            ObjectNode singleResult = mapper.createObjectNode();
            singleResult.put("isIdentical", r.isIdentical());
            singleResult.put("diffImgLocation", r.getDiffImgLocation());
            return singleResult;
        }).collect(Collectors.toList());

        ArrayNode resultsNodes = mapper.createArrayNode();

        resultsNodes.addAll(allResultNodes);

        resultJson.put(RESULTS, resultsNodes);
    }

    private void updateMetaDataTag(final ObjectNode resultJson, List<Result> result) {
        long numberOfDiff = result.stream().filter(r -> !r.isIdentical()).count();
        ObjectNode summary = mapper.createObjectNode();
        summary.put("hasDifference", numberOfDiff > 0);
        summary.put("totalDifference", numberOfDiff);
        resultJson.put(SUMMARY, summary);
    }

    private String error(JsonProcessingException e) {
        ObjectNode errorNode = mapper.createObjectNode();
        errorNode.put("hasException", e.toString());
        return null;
    }

}
