# Skill-Test

This repository is for William and Mary’s CSCI 435 course Software Engineering. This repository contains the files for our programming skill test for the course. The purpose of the program is to search through files in the given directory and see if matching XML and png sets are there. If so, a newly edited png file with yellow boxes around leaf level GUI components in the Android application screenshot is created. Finally, if any edited png files were created, it will move them into a subdirectory named Edited-Pngs. The Programming-Assignment-Data folder contains the files given to us (the XML and matching png files). The Skill Test folder contains my skillTest.java program as well as the matching skillTest.class. The folder also contains another folder called Edited-Pngs. This folder contains the edited pngs from the Programming-Assignment-Data Folder. When skillTest.java is run this folder will be replaced each time. This program was coded in Java.  

  

The skillTest.java program needs 1 argument to run, which is the path of the Programming-Assignment-Data folder or any folder containing XML and matching png files to be boxed. Without an argument, the program will just output, “Please specify a path.” Therefore, to compile and run the program looks something like the following (except with a different path most likely):  

  

Javac skillTest.java  

Java skillTest /Users/admin/Downloads/Programming-Assignment-Data  

  

The program will then run. In the directory where skillTest.java and skillTest.class are, there will be an Edited-Pngs folder (new or replaced) containing the corresponding pngs with yellow boxes around the leaf level GUI components in each of the Android application screenshots from the Programming-Assignment-Data folder.  

  

A general overview of how the program works and the thought process behind my decisions is below.  

  

First, the program takes the path for the directory containing the pngs and XML files. I then decided to create an array of all the files in the designated directory. From there I decided to cycle through each file in the directory to see if there was a matching file with the same name. In order to avoid repetition, I only called the parser method if the first file being compared was the XML file instead of the png. In order to parse the XML file in the parser method, I use the org.w3c.dom library. I then made a nodeList of all the nodes from the XML file. Then I iterated through each node and looked at its class attribute. This is where I looked to see if the attribute contains certain words like Text or String. I think there may be a better way to identify leaf-level GUI components, but I couldn’t think of another way to identify them. If the node contained an acceptable attribute, I then looked at its bound attribute for the boundary of the component. I then added these to an array list of integers. After cycling through all the nodes in the XML file, I then called the method rectangle with the given parameters of the boundary array list and the png file. The rectangle method uses the BufferedImage, ImageIO, and Graphics2D libraries to create a new png file that is the same image as the original png file. Then for each set of 4 boundary coordinates from the coordinates array list a box is drawn using the graphics method drawRect. For the width and the height, I subtracted the 1st boundary coordinates from the 2nd boundary coordinates. After this file is created, the program will continue looking at files in the directory and repeating the cycle until none are left. After there are no more files left, the folder Edited-Pngs is created in the current directory and all the editedcopy png files are moved there.  

  

I’ll be honest the program takes a bit of time to run (about a minute or so), and I definitely think there are more efficient and effective ways to solve this problem. When designing and implementing this program I wasn’t really thinking about efficiency, more about getting the program to compile and run without errors. I think that is where many of my design decisions come from and why a lot of the programs are made from basic and simple java code. If I were to keep on improving and working on this program, I would look at making it run more efficiently, and I think I would also look into understanding the XML attributes better and seeing if I could more efficiently identify the leaf-level GUI components. Lastly, I think my design decision to code the program in java, was because during the first class I think it was mentioned that most of the semester-long projects would be in Java. I wanted to use this skill test not only to show where I am as a programmer but also to brush up on my Java before the projects begin.  
