/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.extension.siddhi.execution.extrema;

import org.wso2.extension.siddhi.execution.extrema.util.AbstractTopKBottomKFinder;
import org.wso2.extension.siddhi.execution.extrema.util.BottomKFinder;
import org.wso2.extension.siddhi.execution.extrema.util.Constants;
import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;

/**
 * bottomKLengthBatch counts the frequency of different values of a specified attribute inside
 * a batch window, and emits the lowest (k) number of frequency values.
 */
@Extension(
        name = "bottomKLengthBatch",
        namespace = "extrema",
        description = "`bottomKLengthBatc` counts the frequency of different values of a specified " +
                "attribute inside a batch window, and returns the specified number of least frequently occuring " +
                "values. The bottom K frequency values are returned per batch.",
        parameters = {
                @Parameter(name = "attribute",
                        description = "The attribute of which the frequency is counted.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.STRING, DataType.BOOL, DataType.OBJECT}),
                @Parameter(name = "window.length",
                        description = "The length of the window observed.",
                        type = {DataType.INT}),
                @Parameter(name = "k.value",
                        description = "The number of bottom frequencies required.",
                        type = {DataType.INT})
        },
        returnAttributes = {
                @ReturnAttribute(
                        name = "bottomNElement",
                        description = "The value of the attribute that has the nth lowest frequency. Here, " +
                                "N is an integer that can have the values of 1 <= N <= k.value, where k.value is " +
                                "defined as the function parameter.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.STRING, DataType.BOOL, DataType.OBJECT}
                ),
                @ReturnAttribute(
                        name = "bottomNFrequency",
                        description = "The frequency of the value of the attribute that has the nth lowest " +
                                "frequency. Here, N is an integer that can have the values of 1 <= N <= k.value, " +
                                "where k.value is defined as the function parameter.",
                        type = {DataType.LONG}
                )
        },
        examples = {
                @Example(
                        syntax = "define stream inputStream (item string, price long);\n" +
                                "\n" +
                                "from inputStream#extrema:bottomKLengthBatch(item, 6, 3)\n" +
                                "insert all events into outputStream;)",
                        description = "This query collects a batch of six events. Once the batch window is full, " +
                                "the three items with the lowest frequency are output, and the window is reset."
                )
        }
)
public class BottomKLengthBatchStreamProcessorExtension extends AbstractKLengthBatchStreamProcessorExtension {
    @Override
    protected AbstractTopKBottomKFinder<Object> createNewTopKBottomKFinder() {
        return new BottomKFinder<Object>();
    }

    @Override
    protected String getExtensionNamePrefix() {
        return Constants.TOP_K_BOTTOM_K_BOTTOM;
    }
}
