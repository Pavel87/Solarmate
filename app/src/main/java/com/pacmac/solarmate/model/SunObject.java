package com.pacmac.solarmate.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pacmac on 08/05/16.
 */

public class SunObject {

        private Results results;
        private String status;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The results
         */
        public Results getResults() {
            return results;
        }

        /**
         *
         * @param results
         * The results
         */
        public void setResults(Results results) {
            this.results = results;
        }

        /**
         *
         * @return
         * The status
         */
        public String getStatus() {
            return status;
        }

        /**
         *
         * @param status
         * The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

