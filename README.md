# openweather_api_use
## Introduction
This was one of my final projects from **2º year** in my university. It uses an Api to recolect information about the weather in London and predict it. It was made with Intellij and Thonny, following clean code principles such as MVC and SOLID. The module is connected with topics and a sql database.

## Use
Each application has his own module, and have to be executed separetly. 

### datalake-builder
Builds the datalake from the topic. 

### sensor
Downloads the data from the api of open weather and pushes the information through a topic.

### analytics
Reads the datalake and shows graphs from the data.
