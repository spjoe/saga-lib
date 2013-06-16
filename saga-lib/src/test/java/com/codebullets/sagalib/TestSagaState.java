package com.codebullets.sagalib;

/**
 * x
 */
public class TestSagaState implements SagaState {
    private String sagaId;
    private String type;
    private String instanceKey;

    @Override
    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(final String sagaId) {
        this.sagaId = sagaId;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String instanceKey() {
        return instanceKey;
    }

    public void setInstanceKey(final String instanceKey) {
        this.instanceKey = instanceKey;
    }
}