package com.patternmatch.ecs.textprocessor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
@JsonDeserialize(builder = TextInput.TextInputBuilder.class)
public class TextInput {

    private String text;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class TextInputBuilder {

    }
}
