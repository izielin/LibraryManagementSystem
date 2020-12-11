# LibraryManagementSystem

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Run using IntelliJ](#Run using IntelliJ)

## General info


## Technologies
Project is created with following technologies:
* JavaFx 15


## Run using IntelliJ
If you want to run this app using IntelliJ you have to Add VM options.
1. From the main menu, select ```Run | Edit Configurations```.
2. Select ```Application | Main``` from the list on the left.
3. In the VM options field, specify: ``` --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml```
4. Instead of ```%PATH_TO_FX%```, specify the path to the JavaFX SDK lib directory, for example: ```"C:\Program Files\/javafx-sdk-11/lib"```.
5. Apply the change and close the dialog.