/*
 * Copyright 2015 the original author or authors.
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
package org.glowroot.local.store;

public interface CappedDatabaseStatsMXBean {

    Stats getAggregateQueries();
    Stats getAggregateProfiles();
    Stats getTraceEntries();
    Stats getTraceProfiles();

    public class Stats {

        private long totalBytesBeforeCompression;
        private long totalBytesAfterCompression;
        private long totalMicros;
        private long totalWrites;

        public long getTotalBytesBeforeCompression() {
            return totalBytesBeforeCompression;
        }

        public long getTotalBytesAfterCompression() {
            return totalBytesAfterCompression;
        }

        public double getTotalMillis() {
            return totalMicros / 1000.0;
        }

        public long getTotalWrites() {
            return totalWrites;
        }

        public double getCompressionRatio() {
            return (totalBytesBeforeCompression - totalBytesAfterCompression)
                    / (double) totalBytesBeforeCompression;
        }

        public double getAverageBytesPerWriteBeforeCompression() {
            return totalBytesBeforeCompression / (double) totalWrites;
        }

        public double getAverageBytesPerWriteAfterCompression() {
            return totalBytesAfterCompression / (double) totalWrites;
        }

        public double getAverageMillisPerWrite() {
            return totalMicros / (double) (1000 * totalWrites);
        }

        void record(long bytesBeforeCompression, long bytesAfterCompression, long micros) {
            totalBytesBeforeCompression += bytesBeforeCompression;
            totalBytesAfterCompression += bytesAfterCompression;
            totalMicros += micros;
            totalWrites++;
        }
    }
}
