[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/AIO_Bc8D)

# Project description

ChaosGame is an application used to create and experiment with various different fractals and transformations.
The default transform types supported in the application are Affine and Julia transformations.

# Project structure

The classes in the project are structured in packages depending on their involvement in the application.
The packages are separated into three main categories: "entity", "logic" and "ui".

# How to run the application

To run the application java 21 should be installed on your machine along maven.
The application can be downloaded from the Releases page on GitHub or by downloading the latest source code as a zip 
file.
Whichever method is used the application will be run in the same way.

Once the application is downloaded and extracted, navigate to the root directory of the project and run the following
```powershell
mvn javafx:run
```

The applcation can also be run from an IDE by running the main class `Main` in the `edu.ntnu.mappe08` package.

# How to use the application

Once the application has started you will be greeted with a window that has a menu bar at the top and a canvas in the
middle. The canvas is where the fractals will be drawn.
The window will be empty until a fractal is loaded. 

## Loading fractals

### Fractals from file
To load a fractal, click on the "File" menu in the menu bar and select "Load Fractal". This will open a file chooser
dialog where you can select a fractal file to load. Default fractals are stored in the "sampleFractals" directory in the
root of the project.
Custom fractals can also be loaded this way.

### Default fractals from the menu
The application comes with a few default fractals that can be loaded from the "Fractals" menu in the menu bar.
Selecting a fractal from the menu will load the fractal into the canvas and replace the currently loaded fractal.

## Saving fractals

### Save fractal to file
Once a fractal is created with the transforms and parameters you want, the file can be saved using the file menu and
selecting "Save Fractal". This will open a file chooser dialog where you can select the location and name of the file to
save the fractal to.

## Panning and zooming

The canvas can be panned by clicking and dragging the mouse on the canvas. The canvas can be zoomed in and out using the
mouse wheel.

## Help menu

The help menu describes the application and how to use it as well as some information about the creators and the reason 
for creating this application.


