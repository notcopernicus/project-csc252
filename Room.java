//Harmony Barber
//Project 4: StudySpace Finder System
//Room Management System

import java.util.*;

public class Room
{
   String roomID;
   int capacity;
   String features;
   public Room(String roomID, int capacity, String features)
   {
        this.roomID = roomID;
        this.capacity = capacity;
        this.features = features;
    }
    public String toString() 
    {
        return "RoomID: " + roomID +
               ", Capacity: " + capacity +
               ", Features: " + features;
    }
    
}