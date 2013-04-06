/*
 * Copyright 2013 Jakob Vad Nielsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jakobnielsen.imagga.crop_slice.bean;

public class Usage {

    private final Long count;

    private final Double totalPrice;

    public Usage(Long count, Double totalPrice) {
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public Long getCount() {
        return count;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}
