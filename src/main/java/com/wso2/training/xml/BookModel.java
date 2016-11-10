/*
 *
 *   Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 * /
 */

package com.wso2.training.xml;

import java.util.HashMap;
import java.util.Map;

class BookModel {
    // Element contents of the parent <book>
    private HashMap<String, String> bookContent;

    private BookModel() {
        this.bookContent = new HashMap<>();
    }

    static BookModel newInstance() {
        return new BookModel();
    }


    /**
     * Put content/info of BookModel as key-value pairs
     * ex: key - title, value - Harry Potter..
     * @param key ex: title, author, description
     * @param value ex: Harry Potter, J. K. Rowling
     */
    void putKeyValue(String key, String value) {
        bookContent.put(key, value);
    }

    /**
     * @return string containing elements of the BookModel
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : bookContent.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return stringBuilder.toString();
    }
}
