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
package com.codebullets.sagalib;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Tests class containing expected annotations.
 */
public class TestSaga extends AbstractSaga<TestSagaState> {
    private boolean startupCalled;
    private boolean handerCalled;

    @StartsSaga
    public void sagaStartup(String startedByString) {
        startupCalled = true;
    }

    @EventHandler
    public void handlesIntegerType(Integer intValue) {
        handerCalled = true;
    }

    public boolean startupCalled() {
        return startupCalled;
    }

    public boolean handerCalled() {
        return handerCalled;
    }

    @Override
    public void createNewState() {
        setState(new TestSagaState());
    }

    @Override
    public Collection<KeyReader> keyReaders() {
        return new ArrayList<>();
    }

    public static Method startupMethod() throws NoSuchMethodException {
        return TestSaga.class.getMethod("sagaStartup", String.class);
    }

    public static Method handlerMethod() throws NoSuchMethodException {
        return TestSaga.class.getMethod("handlesIntegerType", Integer.class);
    }
}