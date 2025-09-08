package com.sangura.Errand_Service.Dtos;

public class ErrandDto {

        private String description;

        private String location;

        private String specialInstructions;

        @Override
        public String toString() {
                return "ErrandDto{" +
                        "description='" + description + '\'' +
                        ", location='" + location + '\'' +
                        ", specialInstructions='" + specialInstructions + '\'' +
                        '}';
        }

        public ErrandDto() {
        }

        public ErrandDto(String description, String location, String specialInstructions) {
                this.description = description;
                this.location = location;
                this.specialInstructions = specialInstructions;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getLocation() {
                return location;
        }

        public void setLocation(String location) {
                this.location = location;
        }

        public String getSpecialInstructions() {
                return specialInstructions;
        }

        public void setSpecialInstructions(String specialInstructions) {
                this.specialInstructions = specialInstructions;
        }
}
