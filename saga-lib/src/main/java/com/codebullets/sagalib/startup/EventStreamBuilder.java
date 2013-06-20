/*
 * Copyright 2013 Stefan Domnanovits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codebullets.sagalib.startup;

import com.codebullets.sagalib.MessageStream;
import com.codebullets.sagalib.processing.HandlerInvoker;
import com.codebullets.sagalib.processing.KeyExtractor;
import com.codebullets.sagalib.processing.ReflectionInvoker;
import com.codebullets.sagalib.processing.SagaFactory;
import com.codebullets.sagalib.processing.SagaKeyReaderExtractor;
import com.codebullets.sagalib.processing.SagaMessageStream;
import com.codebullets.sagalib.processing.SagaProviderFactory;
import com.codebullets.sagalib.storage.StateStorage;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a new instance of an {@link com.codebullets.sagalib.MessageStream} to run the saga lib.
 */
public final class EventStreamBuilder implements StreamBuilder {
    private HandlerInvoker invoker;
    private SagaAnalyzer sagaAnalyzer;
    private TypeScanner scanner;
    private StateStorage storage;
    private SagaProviderFactory providerFactory;

    /**
     * Prevent instantiation from outside. Use {@link #configure()} instead.
     */
    private EventStreamBuilder() {
    }

    /**
     * Start configuration and creation of the saga lib event stream.
     */
    public static StreamBuilder configure() {
        return new EventStreamBuilder();
    }

    @Override
    public MessageStream build() {
        if (providerFactory == null) {
            throw new UnsupportedOperationException("The saga provider factory has to be set to build the event stream.");
        }

        buildTypeScanner();
        buildSagaAnalyzer();
        buildInvoker();

        KeyExtractor extractor = new SagaKeyReaderExtractor(providerFactory);
        SagaFactory sagaFactory = new SagaFactory(sagaAnalyzer, providerFactory, extractor, storage);

        return new SagaMessageStream(sagaFactory, invoker, storage);
    }

    @Override
    public StreamBuilder usingScanner(final TypeScanner typeScanner) {
        checkNotNull(typeScanner, "Scanner to use must not be null.");

        this.scanner = typeScanner;
        return this;
    }

    @Override
    public StreamBuilder usingStorage(final StateStorage stateStorage) {
        checkNotNull(stateStorage, "Storage to use must not be null.");

        this.storage = stateStorage;
        return this;
    }

    @Override
    public StreamBuilder usingSagaProviderFactory(final SagaProviderFactory sagaProviderFactory) {
        checkNotNull(sagaProviderFactory, "Provider factory must be set.");

        this.providerFactory = sagaProviderFactory;
        return this;
    }

    private void buildTypeScanner() {
        if (scanner != null) {
            // scanner = new
        }
    }

    private void buildSagaAnalyzer() {
        if (sagaAnalyzer == null) {
            sagaAnalyzer = new AnnotationSagaAnalyzer(scanner);
        }
    }

    private void buildInvoker() {
        if (invoker == null) {
            invoker = new ReflectionInvoker(sagaAnalyzer);
        }
    }
}
